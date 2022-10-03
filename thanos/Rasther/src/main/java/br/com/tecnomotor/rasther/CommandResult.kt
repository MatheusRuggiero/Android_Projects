package br.com.tecnomotor.rasther

import br.com.tecnomotor.device.EnumCommandResult

class CommandResult(val cmd: ByteArray) {
    var data: ByteArray = byteArrayOf()
    var result: EnumCommandResult = EnumCommandResult.NoneResponse
}