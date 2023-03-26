package net.minecraftforge.fml.common.eventhandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ASMEventHandlerWrapper implements IEventListener
{
    private Object instance = null;

    private final boolean parRun;

    private final Method listenerMethod;

    public ASMEventHandlerWrapper(Method callback, Object obj, boolean parallelRun)
    {
        parRun = parallelRun;
        instance = obj;
        listenerMethod = callback;
    }

    public ASMEventHandlerWrapper(Method callback, boolean parallelRun)
    {
        parRun = parallelRun;
        listenerMethod = callback;
    }

    @Override
    public void invoke(Event event)
    {
        try
        {
            listenerMethod.invoke(instance == null ? null : instance, event); // If instaces == null listenerMethod is static
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean parallelExecution()
    {
        return parRun;
    }
}
