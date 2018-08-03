/**
 * Copyright 2018 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package pt.up.fe.specs.util.logging;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores SpecsLogger instances.
 * 
 * @author JoaoBispo
 *
 */
public class SpecsLoggers {

    private static final Map<String, LoggerWrapper> LOGGERS = new ConcurrentHashMap<>();

    static LoggerWrapper getLogger(String loggerName) {
        LoggerWrapper logger = LOGGERS.get(loggerName);

        if (logger == null) {
            logger = new LoggerWrapper(loggerName);
            // System.out.println("CREATED " + loggerName);
            LOGGERS.put(loggerName, logger);
        } else {
            // System.out.println("RETURNING " + loggerName);
        }

        return logger;
    }

    static LoggerWrapper getLogger(Class<?> aClass, String tag) {

        String loggerName = aClass.getName() + "." + tag;
        // }

        // static SpecsLoggerV2 getLogger(SpecsLogger baseLogger, String loggerName) {
        LoggerWrapper logger = LOGGERS.get(loggerName);

        if (logger == null) {
            logger = new LoggerWrapper(loggerName);
            LOGGERS.put(loggerName, logger);
        }

        return logger;
    }

}
