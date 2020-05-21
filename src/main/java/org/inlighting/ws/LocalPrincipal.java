package org.inlighting.ws;

import java.security.Principal;

/**
 * 用来区分用户，这里传入的name就是该用户的唯一标识
 */
public class LocalPrincipal implements Principal {

    /**
     * The Name.
     */
    private String name;

    /**
     * Instantiates a new Local principal.
     */
    public LocalPrincipal() {
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Instantiates a new Local principal.
     *
     * @param name the name
     */
    public LocalPrincipal(String name) {
        this.name = name;
    }

}