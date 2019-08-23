import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author issuser
 * @date 2019-08-23 22:07
 * @Column(name="month",columnDefinition="nvarchar(6) NOT NULL COMMENT '处理月'")
 */
public class ReflectUtil {


    public static Map<String, String> getSqlFiled(String classpath) {
        Class c = null;
        try {
            c = Class.forName(classpath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = c.getDeclaredFields();
        /*设置字段属性可见*/
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
        }
        /*获取数据库字段与中文名称的对应关系*/
        Map<String, String> sqlFiledMap = new HashMap<String, String>();
        for (int i = 0; i < fields.length; i++) {
            Field field = null;
            try {
                field = c.getDeclaredField(fields[i].getName());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            Column column = field.getAnnotation(Column.class);
            if (column != null && column.columnDefinition().toString().contains("COMMENT")) {
                String value = column.columnDefinition().toString().split("COMMENT")[1].trim().replaceAll("'", "");
                sqlFiledMap.put(value, column.name());
            }
        }
        return sqlFiledMap;
    }

    public static Map<String, String> getJavaFiled(String classpath) {
        Class c = null;
        Field[] fields = null;
        try {
            c = Class.forName(classpath);
            fields = c.getDeclaredFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*首先获取字段与数据类型*/
        Map<String, String> javaFiledMap = new HashMap<String, String>(200);
        if (null != fields && fields.length > 0) {
            for (Field s : fields) {
                String type = "";
                for (EnumTest EnumTest : EnumTest.values()) {
                    if (s.getType().toString().contains(EnumTest.toString())) {
                        type = EnumTest.toString();
                        break;
                    }
                }
                if (!"".equals(type)) {
                    javaFiledMap.put(s.getName(), type);
                }
            }

        }
        return javaFiledMap;
    }

    public static String sqlToJava(String str) {
        if (str.contains("_")) {
            String[] array = str.split("_");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                String s = array[i];
                if (i != 0) {
                    sb.append(s.substring(0, 1).toUpperCase() + s.substring(1));
                } else {
                    sb.append(s);
                }
            }
            return sb.toString();
        }
        return str;
    }

    public static Map<Integer, String> initType(String classPath, String[] headArray) {
        Map<String, String> javaMap = getJavaFiled(classPath);
        Map<String, String> sqlMap = getSqlFiled(classPath);
        Map<Integer, String> map = new HashMap<Integer, String>(headArray.length);
        for (int i = 0; i < headArray.length; i++) {
            String str = headArray[i];
            map.put(i, javaMap.get(sqlToJava(sqlMap.get(str))));
        }
        return map;
    }

    public static String[] filterArray(String[] values, String[] headArray, String fileNameDate, String classPath) {
        Map<Integer, String> map = initType(classPath, headArray);
        String[] arrayTmp = new String[headArray.length];
        System.arraycopy(values, 0, arrayTmp, 0, values.length);
        for (int i = 0; i < arrayTmp.length; i++) {
            if (arrayTmp[i] == null) {
                if (EnumTest.valueOf(map.get(i)).getIndex() == 8) {
                    arrayTmp[i] = fileNameDate;
                } else {
                    arrayTmp[i] = EnumTest.valueOf(map.get(i)).getDesc();
                }
            } else {
                /*因为逗号分隔符会导致sqoop导入异常 必须统一处理*/
                String value = arrayTmp[i].replaceAll(",", "").trim();
                /*对于已经存在的值执行统一接口过滤*/
                switch (EnumTest.valueOf(map.get(i)).getIndex()) {
                    case 0:
                        if (value.contains(".00")) {
                            value = value.replace(".00", "");
                        } else if ("00000000".equals(value)) {
                            value = "0";
                        } else {
                            value = value.replaceAll("^(0+)", "");
                        }
                        arrayTmp[i] = value;
                        break;
                    case 8:
                        if (value.contains(".00")) {
                            value = value.replace(".00", "");
                        }
                        break;
                    default:
                        break;
                }
                arrayTmp[i] = value;
            }
        }
        values = arrayTmp;
        return values;
    }

    public static <T> T cast(String classpath, String[] valueList, SimpleDateFormat dateFormatter, T dataObject) throws Exception {
        Class c = null;
        try {
            c = Class.forName(classpath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = c.getDeclaredFields();
        /*设置字段属性可见*/
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
        }
        for (int i = 0; i < fields.length; i++) {
            String value = valueList[i];
            String name = fields[i].getName();
            EnumTest type = EnumTest.valueOf(fields[i].getType().toString());

            String methodNameSet = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method setMethod = null;
            try {
                if (type == EnumTest.String) {
                    setMethod = dataObject.getClass().getMethod(methodNameSet, String.class);
                    setMethod.invoke(dataObject, value);
                    continue;
                }


                value = value.trim().replace(",", "");

                switch (type) {
                    case Short:
                        setMethod = dataObject.getClass().getMethod(methodNameSet, Short.class);
                        setMethod.invoke(dataObject, Short.parseShort(value));
                        break;
                    case Integer:
                        setMethod = dataObject.getClass().getMethod(methodNameSet, Integer.class);
                        setMethod.invoke(dataObject, Integer.parseInt(value));
                        break;
                    case Long:
                        setMethod = dataObject.getClass().getMethod(methodNameSet, Long.class);
                        setMethod.invoke(dataObject, Long.parseLong(value));
                        break;
                    case Float:
                        setMethod = dataObject.getClass().getMethod(methodNameSet, Float.class);
                        setMethod.invoke(dataObject, Float.parseFloat(value));
                        break;
                    case Double:
                        setMethod = dataObject.getClass().getMethod(methodNameSet, Double.class);
                        setMethod.invoke(dataObject, Double.parseDouble(value));
                        break;
                    case BigDecimal:
                        setMethod = dataObject.getClass().getMethod(methodNameSet, BigDecimal.class);
                        setMethod.invoke(dataObject, new BigDecimal(value));
                        break;
                    case Date:
                        if (value.contains(".")) {
                            value = value.replace(".00", "");
                        }
                        setMethod = dataObject.getClass().getMethod(methodNameSet, Date.class);
                        setMethod.invoke(dataObject, dateFormatter.parseObject(value));
                        break;

                    default:
                        return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
//                throw new Exception(name+"类型错误",e);

            }
        }


        return dataObject;

    }
}
