/*
 * Copyright [2013-2021], Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.polardbx.optimizer.config.table.statistic;

import com.alibaba.polardbx.optimizer.config.table.statistic.inf.StatisticResultSource;

/**
 * statistic result. value&source
 *
 * @author jilong.ljl
 */

/**
 * 此类的作用为封装统计信息的结果，包括统计值和统计值的来源。
 */
public class StatisticResult {
    private Object value;

    /**
     * 枚举类型 StatisticResultSource 用于表示统计结果的来源。
     */
    private StatisticResultSource source;
    private StatisticTrace trace;

    /** * 静态方法 build() 用于创建一个新的 StatisticResult 实例。
     * @return 返回一个新的 StatisticResult 对象。
     */
    public static StatisticResult build() {
        return new StatisticResult();
    }

    /** * 静态方法 build(StatisticResultSource source) 用于创建一个新的 StatisticResult 实例，并设置其来源。
     * @param source 统计结果的来源。
     * @return 返回一个新的 StatisticResult 对象，其来源已设置为传入的 source 参数。
     */
    public static StatisticResult build(StatisticResultSource source) {
        return new StatisticResult().setSource(source);
    }

    /**
     * 方法 getValue() 用于获取统计结果的值。
     * @return 返回统计结果的值。
     */
    public Object getValue() {
        return value;
    }

    /**
     * 方法 getBooleanValue() 用于获取统计结果的布尔值。
     * @return 返回统计结果的布尔值。
     */
    public boolean getBooleanValue() {
        return ((Boolean) value).booleanValue();
    }

    /**
     * 方法 getLongValue() 用于获取统计结果的长整型值。
     * @return 返回统计结果的长整型值。
     */
    public long getLongValue() {
        return ((Number) value).longValue();
    }

    /**
     * 方法 setValue(Object value, StatisticTrace trace) 用于设置统计结果的值和跟踪信息。
     * @param value
     * @param trace
     * @return
     */
    public StatisticResult setValue(Object value, StatisticTrace trace) {
        this.value = value;
        this.trace = trace;
        return this;
    }

    /**
     * 方法 getSource() 用于获取统计结果的来源。
     * @return 返回统计结果的来源。
     */
    public StatisticResultSource getSource() {
        return source;
    }

    /** * 方法 setSource(StatisticResultSource source) 用于设置统计结果的来源。
     * @param source 统计结果的来源。
     * @return 返回当前的 StatisticResult 对象，以便进行链式调用。
     */
    public StatisticResult setSource(StatisticResultSource source) {
        this.source = source;
        return this;
    }

    /**
     * 方法 toString() 用于将 StatisticResult 对象转换为字符串表示形式。
     * @return
     */
    @Override
    public String toString() {
        if (this.getSource() == StatisticResultSource.NULL) {
            return "empty";
        }
        return value + ":" + source.name();
    }

    /**
     * 方法 getTrace() 用于获取统计结果的跟踪信息。
     * @return 返回统计结果的跟踪信息。
     */
    public StatisticTrace getTrace() {
        return trace;
    }

    /**
     * 方法 setTrace(StatisticTrace trace) 用于设置统计结果的跟踪信息。
     * @param trace
     */
    public void setTrace(StatisticTrace trace) {
        this.trace = trace;
    }
}
