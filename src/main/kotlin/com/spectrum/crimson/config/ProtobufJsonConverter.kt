package com.spectrum.crimson.config

import com.google.protobuf.util.JsonFormat

object ProtobufJsonConverter {
    val parser = JsonFormat.parser().ignoringUnknownFields()!!
    val printer = JsonFormat.printer().omittingInsignificantWhitespace().alwaysPrintFieldsWithNoPresence()!!
}