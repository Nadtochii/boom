package com.example.sasha.myapplication.events;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Class helper, that implements Mediator pattern
 * to manage events inside application
 */
public class EventsManager {

    public interface EventHandler {
        void handleEvent(EventType eventType, Object eventData);
    }

    private static EventsManager sInstance;

    private Context mContext;
    private HashMap<EventType, ArrayList<EventHandler>> mEventHandlers;

    private EventsManager(Context context) {
        mContext = context;
        mEventHandlers = new HashMap<>();
    }

    public static void init(Context context) {
        if (sInstance == null)
            sInstance = new EventsManager(context);
    }

    public static void terminate() {
        if (sInstance != null)
            sInstance = null;
    }

    public static void addHandler(EventType eventType, EventHandler handler) {
        assert sInstance != null : "EventsManager must be initialized first";
        assert handler != null   : "Handler cannot be null";

        if (!sInstance.mEventHandlers.containsKey(eventType))
            sInstance.mEventHandlers.put(eventType, new ArrayList<EventHandler>());

        ArrayList<EventHandler> handlers = sInstance.mEventHandlers.get(eventType);
        if (!handlers.contains(handler))
            handlers.add(handler);
    }

    public static void removeHandler(EventType eventType, EventHandler handler) {
        assert sInstance != null : "EventsManager must be initialized first";
        assert handler != null   : "Handler cannot be null";

        if (!sInstance.mEventHandlers.containsKey(eventType))
            return;

        ArrayList<EventHandler> handlers = sInstance.mEventHandlers.get(eventType);
        if (handlers.contains(handler))
            handlers.remove(handler);
    }

    public static void unsubscribeAll(EventHandler handler) {
        assert sInstance != null : "EventsManager must be initialized first";
        assert handler != null   : "Handler cannot be null";

        for (Object o : sInstance.mEventHandlers.entrySet()) {
            HashMap.Entry pair = (HashMap.Entry) o;
            removeHandler((EventType) pair.getKey(), handler);
        }
    }

    public static void dispatchEvent(final EventType eventType, final Object eventData) {
        assert sInstance != null : "EventsManager must be initialized first";

        if (!sInstance.mEventHandlers.containsKey(eventType))
            return;

        Handler mainThreadHandler = new Handler(sInstance.mContext.getMainLooper());
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<EventHandler> handlers = sInstance.mEventHandlers.get(eventType);
                for (int i = 0; i < handlers.size(); ++i) {
                    EventHandler handler = handlers.get(i);
                    handler.handleEvent(eventType, eventData);
                }
            }
        });
    }
}
