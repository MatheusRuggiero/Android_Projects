package br.com.tecnomotor.thanos.service.update

enum class UpdateFileType(val fileType: String) {
    NONE(""), Module("Module"), Executable("Executable"), Data("Data"), Manualtec("Manualtec"), IMAGES(
        "IMAGES"
    ),
    Firmware("Firmware");

}