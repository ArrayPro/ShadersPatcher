package com.arrayprolc.shaderspatcher.core;

import java.io.File;

import com.arrayprolc.shaderspatcher.actions.manager.ActionsManager;

public class ShadersPatcherCore {

    public static void main(String[] args) {
        ActionsManager.registerActions();
        //TEMPORARY
        ActionsManager.getActions().get(0).runAction();
    }

}
