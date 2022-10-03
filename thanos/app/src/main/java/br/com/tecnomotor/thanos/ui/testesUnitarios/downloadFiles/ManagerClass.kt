package br.com.tecnomotor.thanos.ui.testesUnitarios.downloadFiles

import android.annotation.SuppressLint
import br.com.tecnomotor.rasther.utils.AppPaths



/**
@Author Matheus_Ruggiero
 */

class ManagerClass {

    @SuppressLint("SdCardPath")
    fun fileMediator(fileName: String) : String {

        println(" Imprimindo o arquivo na classe mediator $fileName")

        val caminho = when {
            fileName.contains(".atec") -> {

                "/data/data/br.com.tecnomotor.thanos/app_Atec"
            }
            fileName.contains("jpg") -> {
                //AppPaths.DIR_TO_INTERNAL + "/" + AppPaths.DIR_IMAGES
                "/data/data/br.com.tecnomotor.thanos/app_Images"

            }
            fileName.contains(".png") ->{

                "/data/data/br.com.tecnomotor.thanos/app_Positions"
            }
            fileName.contains(".xml") ->{

                "/data/data/br.com.tecnomotor.thanos/files"
            }
            else -> {
                "/data/data/br.com.tecnomotor.thanos/files"
            }
//            fileName?.contains("") == true ->{ TODO verificar depois o database pq eu nao tenho
//                AppPaths.databse + "/" + AppPaths.DIR_CONFIG
//            }
        }
        println(caminho)
        //caminho = "file://" + caminho
        return caminho
    }
}