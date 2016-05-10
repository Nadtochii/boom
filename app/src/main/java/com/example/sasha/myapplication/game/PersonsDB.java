package com.example.sasha.myapplication.game;

import android.content.Context;

import com.example.sasha.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sasha on 10.05.2016.
 */
public class PersonsDB {

    private static PersonsDB sInstance = null;
    private static HashMap<Integer, Integer> sResourcesMap;
    static {
        sResourcesMap = new HashMap<>();
        sResourcesMap.put(1, R.array.persons_level_1);
        sResourcesMap.put(2, R.array.persons_level_2);
    }

    private Context mContext;
    private HashMap<Integer, ArrayList<String>> mPersons;

    private PersonsDB(Context context) {
        mContext = context;

        mPersons = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry: sResourcesMap.entrySet()) {
            int level = entry.getKey(), resourceId = entry.getValue();
            ArrayList<String> persons = (ArrayList<String>) Arrays.asList(
                    mContext.getResources().getStringArray(resourceId));
            mPersons.put(level, persons);
        }
    }

    public static void init(Context context) {
        assert sInstance == null : "PersonsDB already initialized";

        sInstance = new PersonsDB(context);
    }

    public static void terminate() {
        assert sInstance != null : "PersonsDB is not initialized yet";

        sInstance = null;
    }

    public static ArrayList<String> getPersons(int level, int count) {
        assert sInstance != null : "PersonsDB is not initialized yet";
        assert sInstance.mPersons.containsKey(level) : "Unknown level: " + String.valueOf(level);

        ArrayList<String> personsOnLevel = sInstance.mPersons.get(level);

        assert personsOnLevel.size() >= count : "Too much words queried: " + String.valueOf(count);

        Collections.shuffle(personsOnLevel);
        return (ArrayList<String>) personsOnLevel.subList(0, count);
    }
}
