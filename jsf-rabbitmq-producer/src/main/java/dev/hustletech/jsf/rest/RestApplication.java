package dev.hustletech.jsf.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class RestApplication extends Application {
    // JAX-RS will auto-discover our resource classes
}