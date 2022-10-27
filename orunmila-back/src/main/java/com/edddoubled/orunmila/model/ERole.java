package com.edddoubled.orunmila.model;

import java.io.Serializable;

/**
 * rough app role model
 */
public enum ERole implements Serializable {
    /**
     * Can see employees in the context of the project
     */
    OWNER,
    /**
     * Can see employees by position
     */
    SUPEVISOR,
    /**
     * Can see personal data
     */
    WORKER
}
