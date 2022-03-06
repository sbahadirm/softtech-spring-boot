package com.softtech.softtechspringboot.app.sec.enums;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
public enum EnumJwtConstant {

    BEARER("Bearer ")
    ;

    private String constant;
    EnumJwtConstant(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }

    @Override
    public String toString() {
        return constant;
    }
}
