package com.woozooha.adonistrack.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import lombok.Data;

@Data
public class JdbcStatementInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sql;

    private Long start;

    private Long end;

    private Long durationNanoTime;

    private Map<Integer, ObjectInfo> parameterMap;

    private ObjectInfo throwableInfo;

    public void setParameter(Integer index, Object value) {
        if (parameterMap == null) {
            parameterMap = new TreeMap<Integer, ObjectInfo>();
        }

        parameterMap.put(index, new ObjectInfo(value));
    }

    public Double getDurationMiliTime() {
        if (durationNanoTime == null) {
            return null;
        }

        return durationNanoTime.doubleValue() / (1000 * 1000);
    }

    public void calculateDuration() {
        if (start == null || end == null) {
            return;
        }

        durationNanoTime = end - start;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[sql=");
        builder.append(sql);
        if (parameterMap != null) {
            builder.append(", parameterMap=");
            builder.append(parameterMap);
        }
        if (throwableInfo != null) {
            builder.append(", throwableInfo=");
            builder.append(throwableInfo);
        }
        builder.append("]");
        return builder.toString();
    }

}
