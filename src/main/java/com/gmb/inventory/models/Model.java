package com.gmb.inventory.models;

import com.gmb.inventory.views.ViewFactory;

public class Model {
    private final ViewFactory viewFactory;

    private static Model model;
    private Model() {
        viewFactory = new ViewFactory();
    }
    public static synchronized Model getInstance() {
        if(model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
