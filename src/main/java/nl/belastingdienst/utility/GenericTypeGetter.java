package nl.belastingdienst.utility;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;

public enum GenericTypeGetter {
    INSTANCE;

    public <T, S extends T> Class<?>[] getGenericTypes(Class<T> rawType, Class<S> rawSubtype) {
        Type genericType = addGenericTypesBackIn(rawType, rawSubtype);

        if (!(genericType instanceof ParameterizedType))
            return null;

        Type[] targetTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        Class<?>[] result = new Class[targetTypeArguments.length];

        for(int i = 0; i < result.length; i++) {
            if (targetTypeArguments[i] instanceof Class)
                result[i] = (Class<?>) targetTypeArguments[i];
            if (targetTypeArguments[i] instanceof TypeVariable)
                result[i] = getHighestActualArgumentTypeInClassHierarchy((TypeVariable<?>) targetTypeArguments[i], rawSubtype);
        }

        return result;
    }

    private Type addGenericTypesBackIn(Class<?> type, Type subtype) {
        ParameterizedType genericSubtype;
        Class<?> rawSubtype;

        if (subtype instanceof ParameterizedType) {
            genericSubtype = (ParameterizedType) subtype;
            rawSubtype = (Class<?>) ((ParameterizedType) subtype).getRawType();
        } else {
            genericSubtype = null;
            rawSubtype = (Class<?>) subtype;
        }

        if (type.equals(rawSubtype))
            return genericSubtype;

        return addGenericTypesBackIn(type, rawSubtype.getGenericSuperclass());
    }

    private Class<?> getHighestActualArgumentTypeInClassHierarchy(TypeVariable<?> targetTypeArgument, Class<?> subtype) {
        Class<?> result = null;

        TypeVariable<?>[] currentTypeParameters;
        Type[] currentArguments;

        Type genericType = subtype.getGenericSuperclass();
        subtype = subtype.getSuperclass();
        while(subtype != null && !subtype.equals(Object.class)) {
            currentTypeParameters = subtype.getTypeParameters();
            currentArguments = ((ParameterizedType) genericType).getActualTypeArguments();

            for(int i = 0; i < currentArguments.length; i++) {
                if (targetTypeArgument.equals(currentTypeParameters[i])) {
                    result = (currentArguments[i] instanceof TypeVariable)?
                            (Class<?>) currentTypeParameters[i].getBounds()[0]
                            : (Class<?>) currentArguments[i];
                }
            }

            genericType = subtype.getGenericSuperclass();
            subtype = subtype.getSuperclass();
        }

        if (result == null)
            result = (Class<?>) targetTypeArgument.getBounds()[0];

        return result;
    }
}
