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

package com.alibaba.polardbx.server;

import com.alibaba.polardbx.config.ConfigDataMode;
import com.alibaba.polardbx.executor.planmanagement.BaselineSyncController;
import com.alibaba.polardbx.executor.scheduler.ScheduledJobsManager;
import com.alibaba.polardbx.executor.statistic.entity.PolarDbXSystemTableColumnStatistic;
import com.alibaba.polardbx.executor.statistic.entity.PolarDbXSystemTableLogicalTableStatistic;
import com.alibaba.polardbx.executor.statistic.entity.PolarDbXSystemTableNDVSketchStatistic;
import com.alibaba.polardbx.executor.statistic.ndv.NDVSketch;
import com.alibaba.polardbx.gms.config.impl.MetaDbInstConfigManager;
import com.alibaba.polardbx.gms.module.Module;
import com.alibaba.polardbx.gms.module.ModuleLogInfo;
import com.alibaba.polardbx.optimizer.config.table.statistic.StatisticDataSource;
import com.alibaba.polardbx.optimizer.config.table.statistic.StatisticDataTableSource;
import com.alibaba.polardbx.optimizer.config.table.statistic.StatisticManager;
import com.alibaba.polardbx.optimizer.config.table.statistic.inf.NDVSketchService;
import com.alibaba.polardbx.optimizer.config.table.statistic.inf.SystemTableColumnStatistic;
import com.alibaba.polardbx.optimizer.config.table.statistic.inf.SystemTableNDVSketchStatistic;
import com.alibaba.polardbx.optimizer.config.table.statistic.inf.SystemTableTableStatistic;
import com.alibaba.polardbx.optimizer.planmanager.PlanManager;

/**
 * @author fangwu
 */
public class ModuleWarmUp {
    public static void warmUp() {
        if (ConfigDataMode.isFastMock()) {
            MetaDbInstConfigManager.setConfigFromMetaDb(false);
        }
        /**
         * init statistic manager
         */
        //初始化统计管理器。
        //创建系统表统计信息的实例。
        SystemTableTableStatistic systemTableTableStatistic = new PolarDbXSystemTableLogicalTableStatistic();
        //创建系统表列统计信息的实例。
        SystemTableColumnStatistic systemTableColumnStatistic = new PolarDbXSystemTableColumnStatistic();
        //创建系统表 NDV Sketch 统计信息的实例。
        SystemTableNDVSketchStatistic systemTableNDVSketchStatistic = new PolarDbXSystemTableNDVSketchStatistic();
        //创建 NDV Sketch 服务的实例。
        NDVSketchService ndvSketch = new NDVSketch();

        //定义了一个统计数据源 sds。
        StatisticDataSource sds = new StatisticDataTableSource(systemTableTableStatistic, systemTableColumnStatistic,
            systemTableNDVSketchStatistic, ndvSketch);
        StatisticManager.sds = sds;

        //注册统计管理器到模块系统中。
        Module.STATISTICS.register(StatisticManager.getInstance());

        PlanManager.baselineSyncController = new BaselineSyncController();
        Module.SPM.register(PlanManager.getInstance());

        if (!ConfigDataMode.isFastMock()) {
            Module.SCHEDULE_JOB.register(ScheduledJobsManager.getInstance());
        }
        Module.MODULE_LOG.register(ModuleLogInfo.getInstance());
    }
}
