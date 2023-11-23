package com.turismo.security.routes.roles;

import java.util.List;

public class RouteModel {
    private String method;
    private String route;
    private List<String> roles;

    public RouteModel() {
    }

    public RouteModel(String method, String route, List<String> roles) {
        this.method = method;
        this.route = route;
        this.roles = roles;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

