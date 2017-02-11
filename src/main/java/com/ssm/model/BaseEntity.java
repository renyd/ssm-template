package com.ssm.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by Domg on 2017/1/15.
 */
public class BaseEntity implements Serializable ,Comparable ,Cloneable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public int compareTo(Object o) {
        return 0;
    }
}
