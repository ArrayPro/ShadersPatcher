package com.arrayprolc.shaderspatcher.actions.manager;

import java.util.ArrayList;

import com.arrayprolc.shaderspatcher.actions.Action;
import com.arrayprolc.shaderspatcher.actions.ActionCreate;
import com.arrayprolc.shaderspatcher.actions.ActionExtract;

public class ActionsManager {

    private static ArrayList<Action> actions = new ArrayList<Action>();

    public static void registerActions() {
        actions.add(new ActionCreate());
        actions.add(new ActionExtract());
    }

    public static ArrayList<Action> getActions() {
        return actions;
    }

}
