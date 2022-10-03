package br.com.tecnomotor.thanos.repository.menu

class MenuRepository {

    companion object { // singleton
        @Volatile
        private var instance: MenuRepository? = null

        //Singleton instance
        fun getInstance(): MenuRepository {
            return instance ?: synchronized(this) {
                MenuRepository().also { instance = it }
            }
        }
    }

    val plataforma = PlataformaRepository.getInstance()
    val versao = VersaoRepository.getInstance()
    val montadora = MontadoraRepository.getInstance()
    val veiculo = VeiculoRepository.getInstance()
    val motorizacao = MotorizacaoRepository.getInstance()
    val tipoDeSistemas = TipoSistemasRepository.getInstance()
    val sistemas = SistemaRepository.getInstance()
    val conectores = ConectorRepository.getInstance()
    val aplicacaoRepository = AplicacaoRepository.getInstance()
    val historicoRepository = HistoricoRepository.getInstance()
    val favoritosRepository = FavoritosRepository.getInstance()
}