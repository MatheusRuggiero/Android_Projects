const relatorios = [
  {
    relatorioDefeitos: {
      rcd_Id: 01,
      rcd_codigo: "B33DE",
      rcd_descricao: "descricao",
      rcd_sintoma: "sintoma",
      rcd_status: "status",
      rcd_datahora: "05/05/2022-15:00",
      relatorioAplicacao: {
        rea_placa: "ABC-1234",
        rea_monnome: "FORD",
        rea_veinome: "Fusion",
        rea_motnome: "motor",
        rea_tpsnome: "teste",
        rea_sisnome: "sistema",
        rea_anoinicial: "2008",
        rea_anofinal: "2009",
        rea_modulo: 12,
        rea_datahora: "05/05/2022-15:00",
        relatorio: {
          rel_numserie: "d12w4",
          rel_verfirmware: "12.0",
          rel_plataforma: "ABC",
          rel_versao: 11,
          rel_versaoapp: "13.1.3",
        },
      },
    },
    relatorioLeituras: {
      rle_Id: 02,
      rle_tipo: 123,
      rle_nome: "nome",
      rle_valor_minimo: 12.0,
      rle_valor_maximo: 30.0,
      rle_valor: 23.6,
      rle_unidade: "ºC",
      rle_valor_texto: "valor",
      rle_datahora: "06/05/2022-15:00",
      relatorioAplicacao: {
        rea_placa: "ABC-1234",
        rea_monnome: "FORD",
        rea_veinome: "Fusion",
        rea_motnome: "motor",
        rea_tpsnome: "teste",
        rea_sisnome: "sistema",
        rea_anoinicial: "2008",
        rea_anofinal: "2009",
        rea_modulo: 12,
        rea_datahora: "05/05/2022-15:00",
        relatorio: {
          rel_numserie: "d12w4",
          rel_verfirmware: "12.0",
          rel_plataforma: "ABC",
          rel_versao: 11,
          rel_versaoapp: "13.1.3",
        },
      },
    },
    relatorioGraficos: {
      reg_Id: 03,
      reg_nome: "nome",
      reg_img: "img/RPC_GRAFICO.jpg",
      reg_datahora: "06/05/2022-15:00",
      relatorioAplicacao: {
        rea_placa: "ABC-1234",
        rea_monnome: "FORD",
        rea_veinome: "Fusion",
        rea_motnome: "motor",
        rea_tpsnome: "teste",
        rea_sisnome: "sistema",
        rea_anoinicial: "2008",
        rea_anofinal: "2009",
        rea_modulo: 12,
        rea_datahora: "05/05/2022-15:00",
        relatorio: {
          rel_numserie: "d12w4",
          rel_verfirmware: "12.0",
          rel_plataforma: "ABC",
          rel_versao: 11,
          rel_versaoapp: "13.1.3",
        },
      },
    },
    relatorioECU: {
      rec_Id: 04,
      rec_descricao: "descricao",
      rec_valor: "valor",
      rec_datahora: "05/05/2022-15:00",
      relatorioAplicacao: {
        rea_placa: "ABC-1234",
        rea_monnome: "FORD",
        rea_veinome: "Fusion",
        rea_motnome: "motor",
        rea_tpsnome: "teste",
        rea_sisnome: "sistema",
        rea_anoinicial: "2008",
        rea_anofinal: "2009",
        rea_modulo: 12,
        rea_datahora: "05/05/2022-15:00",
        relatorio: {
          rel_numserie: "d12w4",
          rel_verfirmware: "12.0",
          rel_plataforma: "ABC",
          rel_versao: 11,
          rel_versaoapp: "13.1.3",
        },
      },
    },
  },
];

//console.log(relatorios[0].relatorioDefeitos.relatorioAplicacao.rea_modulo);

//================= Variaveis Teste ======================//

var nomeEmpresa = "Tecnomotor Eletrônica do Brasil S/A";
var endereco = "Rua Albino Triques, 2040";
var cidadeEstado = "São Carlos - São Paulo";
var cep = "13560-970";
var telefone = "Tel.: (16)3362-8000";
var email = "tecnomotor@tecnomotor.com.br";
var site = "www.tecnomotor.com.br";

window.onload = function getValue() {
  //============= Info Empresa ===========================//

  document.getElementById("nameCompany").innerHTML = nomeEmpresa;
  document.getElementById("endCompany").innerHTML = endereco;
  document.getElementById("cityEstateCEPCompany").innerHTML =
    cidadeEstado + " - " + cep;
  document.getElementById("telCompany").innerHTML = telefone;
  document.getElementById("emailCompany").innerHTML = email;
  document.getElementById("siteCompany").innerHTML = site;
  //============= FIM Info Empresa ===========================//

  //=============== Info Equipamento ===================//

  document.getElementById("equipInfo").innerHTML =
    "Rasther Android: " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.relatorio.rel_versaoapp +
    " - Firmware: " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.relatorio
      .rel_verfirmware +
    " - Num.Série: " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.relatorio.rel_numserie +
    " - Plataforma: " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.relatorio
      .rel_plataforma +
    " - Versão: " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.relatorio.rel_versao;

  //=============== FIM Info Equipamento ===================//

  //==================== Info Veículo ==========================//

  document.getElementById("montadora").innerHTML =
    "Montadora: " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.rea_monnome;
  document.getElementById("tipoSistema").innerHTML =
    "Tipo de sistema: " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.rea_motnome;
  document.getElementById("sistema").innerHTML =
    "Sistema: " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.rea_sisnome;
  document.getElementById("nomeVeiculo").innerHTML =
    "Veículo: " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.rea_veinome;
  document.getElementById("placa").innerHTML =
    "Placa: " + relatorios[0].relatorioDefeitos.relatorioAplicacao.rea_placa;
  document.getElementById("anoInicialFinal").innerHTML =
    "Ano do veículo: " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.rea_anoinicial +
    " / " +
    relatorios[0].relatorioDefeitos.relatorioAplicacao.rea_anofinal;

  //==================== FIM Info Veículo ==========================//

  //=================== Info Defeitos =============================//

  document.getElementById("rcdCodigo").innerHTML =
    relatorios[0].relatorioDefeitos.rcd_codigo;
  document.getElementById("rcdDescricao").innerHTML =
    relatorios[0].relatorioDefeitos.rcd_descricao;
  document.getElementById("rcdSintoma").innerHTML =
    relatorios[0].relatorioDefeitos.rcd_sintoma;
  document.getElementById("rcdStatus").innerHTML =
    relatorios[0].relatorioDefeitos.rcd_status;
  document.getElementById("rcdDatahora").innerHTML =
    relatorios[0].relatorioDefeitos.rcd_datahora;

  //=================== FIM Info Defeitos =============================//

  //=================== Info Leituras ============================//

  document.getElementById("rleNome").innerHTML =
    relatorios[0].relatorioLeituras.rle_nome;
  document.getElementById("rleValor").innerHTML =
    relatorios[0].relatorioLeituras.rle_valor +
      relatorios[0].relatorioLeituras.rle_unidade ||
    relatorios[0].relatorioLeituras.rle_valor_texto;
  document.getElementById("rleMinMax").innerHTML =
    relatorios[0].relatorioLeituras.rle_valor_minimo +
    " / " +
    relatorios[0].relatorioLeituras.rle_valor_maximo;
  document.getElementById("rleDatahora").innerHTML =
    relatorios[0].relatorioLeituras.rle_datahora;

  //=================== FIM Info Leituras ============================//

  //======================= Info Gráfico =========================//

  document.getElementById("regNome").innerHTML =
    relatorios[0].relatorioGraficos.reg_nome;
  document.getElementById("regImg").innerHTML =
    relatorios[0].relatorioGraficos.reg_img;
  document.getElementById("regDatahora").innerHTML =
    relatorios[0].relatorioGraficos.reg_datahora;

  //======================= FIM Info Gráfico =========================//

  //==================== Info ECU =============================//
  document.getElementById("recDescricao").innerHTML =
    relatorios[0].relatorioECU.rec_descricao;
  document.getElementById("recValor").innerHTML =
    relatorios[0].relatorioECU.rec_valor;
  document.getElementById("recDatahora").innerHTML =
    relatorios[0].relatorioECU.rec_datahora;

  //==================== FIM Info ECU =============================//
  
  
   var teste = Android.jsShowToast("Funcionou ?");
  document.getElementById("nameCompany").innerHTML = teste;
  
  
 
   // $(function() { // 1
    //    function showAndroidToast(toast) { // 2
    //        if (typeof Android !== 'undefined') // 3
     //          var test =  Android.jsShowToast(toast);
      //         alert(test); // 4
      //  }
        
    //   showAndroidToast('{{ webview }}') // 5
   // })


  
      $(function test() {
      if (typeof Android != "undefined"){ // check the bridge   
          alert(data);
          Android.test(data);
          alert(data);
         
      }
      test('{{ webview }}')
   })
  
  
  
  
  
  
  
  
  
};

//================ Pagina em PDF (em teste)=====================//

//---------------- função teste 1 ------------------------------//
// function pdfCreator() {
//   var imgData = "img/RPC_GRAFICO.jpg";
//   const pdfDoc = new jsPDF("p", "pt", "a4");
//   pdfDoc.fromHTML(document.getElementById("content"), 5, 5),
//     {
//       //pdfDoc.addImage(imgData, "JPG", 0, 0, 0, 0);
//       callback: setTimeout(() => {
//         pdfDoc.save("Relatorio.pdf");
//       }, 1000),
//     };
// }

//---------------------- função teste 2 -------------------------//
// $(document).ready(function () {
//   //pdf
//   $("#pdfDownloader").click(function () {
//     html2canvas(document.getElementsByTagName("body"), {
//       onrendered: function (canvas) {
//         var imgData = canvas.toDataURL("image/jpeg");

//         //console.log('Image URL: ' + imgData);

//         var doc = new jsPDF("p", "px", [1085, 1100], true);

//         //doc.setFontSize(10);

//         //doc.text(10, 15, "Filter section will be printed where.");

//         doc.addImage(imgData, "jpeg", 2, 150);

//         doc.save("teste.pdf");
//       },
//     });
//   });
// });
