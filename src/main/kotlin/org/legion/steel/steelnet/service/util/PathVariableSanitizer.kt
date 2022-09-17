package org.legion.steel.steelnet.service.util

import java.util.*

class PathVariableSanitizer : PathVariableSanitizerInterface {

    companion object {
        fun sanitizePathVariable(pathVariable: String): String {
            return pathVariable.replace("~", "´").replace("_", " ").replace(".", "`").lowercase(Locale.getDefault())
        }
    }

}