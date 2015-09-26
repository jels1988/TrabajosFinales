package pe.lindley.tomapedidos.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pe.lindley.tomapedidos.entities.Cliente;

/**
 * Created by jlama on 19/09/2015.
 */
public class ClienteDAO {
    private static ClienteDAO INSTANCE = null;

    private ClienteDAO() {
    }

    public static ClienteDAO getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClienteDAO();
        }
    }

    public List<Cliente> getListaCliente() {
        List<Cliente> lista = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DataBaseHelper.myDataBase.query("TB_Cliente", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Cliente cliente = new Cliente();
                    cliente.setClienteId(cursor.isNull(cursor.getColumnIndex("Cliente_Id")) ? 0 : cursor.getInt(cursor.getColumnIndex("Cliente_Id")));
                    cliente.setClienteRazonSocial(cursor.isNull(cursor.getColumnIndex("Cliente_RazonSocial")) ? "" : cursor.getString(cursor.getColumnIndex("Cliente_RazonSocial")));
                    cliente.setClienteNIT(cursor.isNull(cursor.getColumnIndex("Cliente_NIT")) ? "" : cursor.getString(cursor.getColumnIndex("Cliente_NIT")));
                    cliente.setClienteDireccion(cursor.isNull(cursor.getColumnIndex("Cliente_Direccion")) ? "" : cursor.getString(cursor.getColumnIndex("Cliente_Direccion")));
                    cliente.setClienteGiroNegocio(cursor.isNull(cursor.getColumnIndex("Cliente_GiroNegocio")) ? "" : cursor.getString(cursor.getColumnIndex("Cliente_GiroNegocio")));
                    cliente.setClienteLongitud(cursor.isNull(cursor.getColumnIndex("Longitud")) ? "" : cursor.getString(cursor.getColumnIndex("Longitud")));
                    cliente.setClienteLatitud(cursor.isNull(cursor.getColumnIndex("Latitud")) ? "" : cursor.getString(cursor.getColumnIndex("Latitud")));
                    lista.add(cliente);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return lista;
    }

    public void deleteAll() {
        try {
            DataBaseHelper.myDataBase.execSQL(
                    "DELETE FROM TB_Cliente");
        } catch (Exception ex){
        }
    }

    public void poblarBaseDatos() {
        List<Cliente> lista = new ArrayList<>();
        lista.add(new Cliente( 14419, "LOPEZ FELIX RODOLFO", "AV.CANAVAL Y MOREYRA/BLONDETT", "KIOSKOS", "10078438547", "-12.097002", "-77.022041"));
        lista.add(new Cliente( 14427, "SANTARIA LEON LUCIO", "ESQ. REP. PANAMA Y LAS CASTANI", "CARRETILLAS", "08866800", "-12.092899", "-77.021829"));
        lista.add(new Cliente( 14450, "URBANO GUTIERREZ REYNALDA", "CALLE LOS FLAMENCOS 145", "CARRETILLAS", "09010130", "-12.099244", "-77.020267"));
        lista.add(new Cliente( 14462, "SIHUA CUSIHUALLPA LIBERATA", "ARAMBURU/LOS HALCONES", "KIOSKOS", "07991927", "-12.101859", "-77.019838"));
        lista.add(new Cliente( 14463, "HUAMANI MAYHUIRI MARIA", "AV. ARAMBURU Y LOS HALCONES", "CARRETILLAS", "08926580", "-12.101771", "-77.01985"));
        lista.add(new Cliente( 14478, "TULLIANO SILVERI VICENTE", "AV.J.PRADO ESTE 1010 INT.104.", "CLINICAS/SANATORIOS", "10077758955", "-12.090493", "-77.01882"));
        lista.add(new Cliente( 17456, "CUEVA GARCIA JENNY PILAR", "CA.LOS HALCONES 376", "BODEGAS", "10108029922", "-12.101671", "-77.01988"));
        lista.add(new Cliente( 30561, "AYLLON PARIONA ELADIO JESUS", "AV.JAVIER PRADO ESTE 1104", "BODEGAS", "10095682427", "-12.090329", "-77.017608"));
        lista.add(new Cliente( 30700, "CHAVEZ SANCHEZ NELLY", "AV. CANAVAL MOREYRA 664", "CADENAS DE FARMACIAS", "10082467888", "-12.098007", "-77.01785"));
        lista.add(new Cliente( 54544, "WONG GADEA DE AYO MIRTHA JUDY", "CA.LOS RUISENORES 107", "RESTAURANTE FINO CATEGORIA", "10082562333", "-12.094936", "-77.02029"));
        lista.add(new Cliente( 72073, "MARTINEZ AYALA HILDA N.", "RICARDO ANGULO 753 URB.CORPAC", "PELUQUERIA/SALON DE BELLEZA", "10066508540", "-12.098459", "-77.013614"));
        lista.add(new Cliente( 73947, "INVERSIONES GALLO S.A.", "AV.DEL PARQUE SUR 185", "BODEGAS", "20509132101", "-12.102255", "-77.015861"));
        lista.add(new Cliente( 75812, "TANTALEAN NUNEZ AGUSTIN", "CAL.31   172 URB. CORPAC", "BODEGAS", "10274307582", "-12.106716", "-77.012027"));
        lista.add(new Cliente( 93965, "MODESTO NOLASCO VILLANUEVA", "LAS CASTANITAS CDRA 1", "CARRETILLAS", "08835990", "-12.092638", "-77.019349"));
        lista.add(new Cliente( 114485, "RONDON ESPINOZA MARCO ANTONIO", "CANAVAL MOREYRA 593", "BODEGAS", "10101922826", "-12.097422", "-77.018836"));
        lista.add(new Cliente( 136684, "D.J.PERU CORPORATION S.A.C.", "CA.7 NO. 303", "HOTELES/MOTEL/POSADA", "20507230203", "-12.090853", "-77.009986"));
        lista.add(new Cliente( 161042, "BODEGA RESTAURANT FEN KO S.A.C", "AV.JAVIER PRADO ESTE 1108", "OTROS RESTAURANTES", "20513887397", "-12.090312", "-77.017477"));
        lista.add(new Cliente( 166416, "DE LOS RIOS HUARCAYA FERNANDO", "CA.MAYOR ARMANDO BLONDET 258", "CRIOLLO/PICANTERIA", "10093426318", "-12.096192", "-77.022633"));
        lista.add(new Cliente( 169483, "NEREUS S.A.C.", "AV. PABLO CARRIQUIRRY 298", "HOTELES/MOTEL/POSADA", "20514102113", "-12.094301", "-77.019344"));
        lista.add(new Cliente( 180800, "CANCHARI RAMIREZ DE SEDANO V.", "AV.CANAVAL Y MOREYRA 650", "BEBIDAS EN GENERAL", "10088550299", "-12.097997", "-77.017932"));
        lista.add(new Cliente( 200155, "SEKUR PERU S.A.", "CAL. RICARDO ANGULO 782", "OFICINAS DE NEGOCIOS PROFESI", "20131529008", "-12.09888", "-77.013917"));
        lista.add(new Cliente( 206673, "DANJEN S.A", "LAS CASTANITAS 152", "HOTELES/MOTEL/POSADA", "20252881655", "-12.093013", "-77.021081"));
        lista.add(new Cliente( 261273, "RIVERA CHURA LUCIA", "ZORZALES/REP.PANAMA", "CARRETILLAS", "08825742", "-12.099973", "-77.019091"));
        lista.add(new Cliente( 270415, "MARLO NUNEZ SEGUNDO SIMEON", "CAL.RICARDO ANGULO NRO.780", "BODEGAS", "10102722693", "-12.0988", "-77.013879"));
        lista.add(new Cliente( 270576, "VALENCIA TORRES REYNA ALICIA", "MIGUEL SEMINARIO/P.REPUBLICA", "CARRETILLAS", "09708086", "-12.09493", "-77.023737"));
        lista.add(new Cliente( 270682, "RIVERA CHURA TOMAS", "AV.REPUBLICA DE PANAMA CDRA.35", "CARRETILLAS", "10344248", "-12.099835", "-77.019784"));
        lista.add(new Cliente( 272860, "SERVIJULY S.R.LTDA.", "AV.JAVIER PRADO ESTE N 1114", "OTROS RESTAURANTES", "20260337301", "-12.090154", "-77.016229"));
        lista.add(new Cliente( 273598, "CHAUCCA HUAMANI TRINIDAD", "CAL.RICARDO ANGULO 731 CORPAC", "OTROS RESTAURANTES", "10082627460", "-12.097672", "-77.013556"));
        lista.add(new Cliente( 348562, "MAMANI LIMACHE CARMELO", "AV.CARRIQUIRI/CASTANAS", "CARRETILLAS", "06878100", "-12.092732", "-77.019502"));
        lista.add(new Cliente( 385711, "ZEVALLOS MARIAZZA ZOILA ROSA", "AV.JOSE GALVEZ BARRENECHEA 476", "RESTAURANTES CATEGORIA MEDIA", "10082264146", "-12.096773", "-77.01301"));
        lista.add(new Cliente( 386405, "ESTRELLA CARBAJAL FRANCISCO", "ESQ.CALLE 5/JAVIER PRADO ESTE", "CARRETILLAS", "06047117", "-12.090354", "-77.017773"));
        lista.add(new Cliente( 396841, "PRINCIPE JARA FAUSTA", "CALLE 1 NRO 154 CORPAC", "RESTAURANTE FINO CATEGORIA", "10105334791", "-12.098318", "-77.013474"));
        lista.add(new Cliente( 397918, "TANTALEAN NUNEZ NORVIL", "CAL.34 NO. 251 URB.CORPAC", "BODEGAS", "10407036081", "-12.100831", "-77.016541"));
        lista.add(new Cliente( 408021, "CAMPOS BENDEBU GRIMALDO", "AV.R.PANAMA C-35/FLAMENCOS", "KIOSKOS", "10098044031", "-12.099185", "-77.020051"));
        lista.add(new Cliente( 415967, "PALOMINO MALDONADO FALCONERIO", "CALLE 31 NO.131 URB. CORPAC", "CRIOLLO/PICANTERIA", "10091619666", "-12.107089", "-77.011727"));
        lista.add(new Cliente( 419991, "MINAYA CRUZ CARLOS", "AV.ARAMBURU CDRA 9/8", "KIOSKOS", "09003017", "-12.101951", "-77.021105"));
        lista.add(new Cliente( 421051, "QUISPE HUILLCA MARCOS NESTOR", "CALLE SIETE 369 URB. CORPAC", "HAMBURGUESA", "10077589061", "-12.091479", "-77.010038"));
        lista.add(new Cliente( 435779, "YARANGA YARANGA CARMELA", "CA.21 960 CORPAC", "BODEGAS", "10094947702", "-12.097347", "-77.01323"));
        lista.add(new Cliente( 465836, "CLUTE S.A.", "CAL.RICARDO ANGULO 776 DP.101", "OFICINAS DE NEGOCIOS PROFESI", "20509777145", "-12.098617", "-77.013845"));
        lista.add(new Cliente( 472750, "INVERSIONES KAROSS S.A.C.", "AV.MIGUEL SEMINARIO 310", "PELUQUERIA/SALON DE BELLEZA", "20518758188", "-12.094947", "-77.022292"));
        lista.add(new Cliente( 473123, "SANCHEZ CCOYLLO ELENA", "AV.CARRIQUIRRY/C.MOREYRA", "CARRETILLAS", "08749925", "-12.09713", "-77.018549"));
        lista.add(new Cliente( 474671, "FLORES BENAVENTE OSORIA SABINA", "AV.CARRIQUIRRY/AV.C.MOREYRA", "KIOSKOS", "08840081", "-12.097326", "-77.01854"));
        lista.add(new Cliente( 505146, "TORRES UMASI CATALINA", "AV.REP.PANAMA FTE 3411/CANAVAL", "CARRETILLAS", "09213301", "-12.097771", "-77.019948"));
        lista.add(new Cliente( 508155, "MINIMARKET CORPAC E.I.R.L.", "CA.VALMORE ROCALLA 304 CORPAC", "BODEGAS", "20521489449", "-12.091926", "-77.010338"));
        lista.add(new Cliente( 511235, "CONTRERAS ANA MARIA FLOR", "AV.P.REPUBLICA FTE. 3101", "KIOSKOS", "08357948", "-12.094912", "-77.023776"));
        lista.add(new Cliente( 511237, "RAMOS DOMINGUEZ GREGORIA", "AV.PAS.REPUBLICA 32/C.MOREYRA", "KIOSKOS", "07326712", "-12.096615", "-77.02455"));
        lista.add(new Cliente( 553866, "MALCA FLORES EDITH SOLEDAD", "JR.RICARDO ANGULO 749", "PANADERIA/PASTELERIA", "43200138", "-12.098289", "-77.013597"));
        lista.add(new Cliente( 559770, "SANCHEZ RIZO PATRON S.A.C.", "JR.DIONISIO DE ARTEANO MOD.1", "CRIOLLO/PICANTERIA", "20522649440", "-12.096704", "-77.021875"));
        lista.add(new Cliente( 612762, "ESCA SAC", "AV.R.ANGULO 776 INT.301 CORPAC", "OFICINAS DE NEGOCIOS PROFESI", "20100609151", "-12.098726", "-77.013869"));
        lista.add(new Cliente( 664613, "ARCONDO SUCLLE SONIA", "AV.CANAVAL Y MOREYRA 648", "BODEGAS", "46225860", "-12.098075", "-77.017944"));
        lista.add(new Cliente( 665110, "CARUAJULCA AGIP LILER", "CA.TREINTA Y UNO 110", "BODEGAS", "10429116860", "-12.106672", "-77.011776"));
        lista.add(new Cliente( 673708, "GUILLEN POMA SONIA", "AV.J.PRADO CDRA 10-CARRIQUIRRY", "CARRETILLAS", "07604736", "-12.09052", "-77.019005"));
        lista.add(new Cliente( 704657, "COLLADO ARAGON NORMA ANGELICA", "CL.5 CDRA.1 /RICARDO ANGULO", "CARRETILLAS", "08257312", "-12.09048", "-77.017759"));
        lista.add(new Cliente( 705303, "QUISPE ABURTO MERCEDES V.", "AV.CANAVAL Y MOREYRA 658", "SANDUCHES/EMPNADAS", "10154513308", "-12.097936", "-77.017926"));
        lista.add(new Cliente( 732287, "VASALESAY  SAC", "CA.LOS HOLCONES 118", "HOTELES/MOTEL/POSADA", "20546973507", "-12.098476", "-77.021126"));
        lista.add(new Cliente( 743521, "AROMAS CAFE DELICATESSEN SAC", "AV.REPUBLICA DE PANAMA 3570 -1", "DELICATESSEN", "20536327933", "-12.100131", "-77.019638"));
        lista.add(new Cliente( 752892, "SER.GASTRONOMICOS CONDORI EIRL", "AV.A.ARAMBURU 883 LIMATAMBO", "CONCESIONARIO DE COMIDA", "20536015028", "-12.102047", "-77.021494"));
        lista.add(new Cliente( 780468, "CAFUBA SAC", "CA.LOS HALCONES 126", "OTROS RESTAURANTES", "20515095013", "-12.098522", "-77.021109"));
        lista.add(new Cliente( 813799, "DOMINGUEZ AGUILAR LEONOR", "CAL.21 NO. 930", "BODEGAS", "10081915267", "-12.097382", "-77.013425"));
        lista.add(new Cliente( 841390, "MARCOS MORA MARUJA NATALIA", "AV.PRINCIPAL ESQ.CA.51 CDRA.9", "KIOSKOS", "10074616700", "-12.105252", "-77.011186"));
        lista.add(new Cliente( 848215, "BARRIONUEVO PARIONA GERONIMO", "CA.SEIS 272 URB.CORPAC", "BODEGAS", "10198373198", "-12.091555", "-77.009815"));
        lista.add(new Cliente( 902381, "CECILIA FERNAMDEZ HUARACA", "GAVILANES/GONZALES OLAECHEA", "KIOSKOS", "09711510", "-12.097785", "-77.024266"));
        lista.add(new Cliente( 904197, "PALACIOS MARCOS BELINDA B.", "AV JAVIER PRADO E. S/N EL PALO", "CARRETILLAS", "10082885884", "-12.090482", "-77.019338"));
        lista.add(new Cliente( 906457, "BURGOS PAULINO MARTHA ALICIA", "CA.UNO OESTE 057 URB.CORPAC", "CAFE BAR", "10072338958", "-12.098252", "-77.014366"));
        lista.add(new Cliente( 907755, "KAYPI INVERSIONES S.A.C.", "CA.LOS HALCONES 124", "RESTAURANTE FINO CATEGORIA", "20544123638", "-12.098408", "-77.021154"));
        lista.add(new Cliente( 907876, "NARVAEZ VILLAVICENCIO LEONCIO", "AV P.REPUBLICA FTE 3135", "KIOSKOS", "08846276", "-12.095525", "-77.024035"));
        lista.add(new Cliente( 911382, "A Y C ALIMENTOS SAC", "CANAVAL MOREYRA 480 3ER PISO", "CONCESIONARIO DE COMIDA", "20543964574", "-12.097529", "-77.020843"));
        lista.add(new Cliente( 930741, "CABEZAS DURAN CELESTINO", "CAL.GERMAN SCHREIBER NRO.230", "OTROS RESTAURANTES", "10236791870", "-12.095679", "-77.023531"));
        lista.add(new Cliente( 983293, "ATEQ RUNA S.A.C.", "CA. ARMANDO BLONDET 265", "PIZZA", "20507642498", "-12.096303", "-77.022454"));
        lista.add(new Cliente( 1007628, "QUISPE ARISANCA RUPERTO", "JAVIER PRADO ESTE CDR.9", "KIOSKOS", "07109481", "-12.090487", "-77.019361"));
        lista.add(new Cliente( 1007629, "OSHIRO SHIMABUKURO DANNY", "REPUBLICA DE PANAMA 3165", "CONCESIONARIO DE COMIDA", "40017395", "-12.095702", "-77.020837"));
        lista.add(new Cliente( 1020885, "DISTRIBUIDORA EKAMA S.A", "AV. GUARDIA CIVIL NRO. 816", "BEBIDAS EN GENERAL", "20123110367", "-12.098559", "-77.012994"));
        lista.add(new Cliente( 8807185, "LOS HALCONES SAC", "CALLE SIETE 363", "OFICINAS DE NEGOCIOS PROFESI", "20310555046", "-12.091422", "-77.01001"));
        lista.add(new Cliente( 8812814, "TRADICION FRANCESA S.A.C.", "CA.RICARDO ANGULO 1030", "PANADERIA/PASTELERIA", "20553094691", "-12.101992", "-77.01557"));
        lista.add(new Cliente( 8813128, "SANCHEZ RIZO PATRON S.A.C.", "ARMANDO BLONDET 139", "OTROS RESTAURANTES", "20522649440", "-12.09518", "-77.022392"));

        ContentValues values = null;
        for( Cliente cliente : lista ) {
            values = new ContentValues();
            values.put("Cliente_Id", cliente.getClienteId());
            values.put("Cliente_RazonSocial", cliente.getClienteRazonSocial());
            values.put("Cliente_Direccion", cliente.getClienteDireccion());
            values.put("Cliente_GiroNegocio", cliente.getClienteGiroNegocio());
            values.put("Cliente_NIT", cliente.getClienteNIT());
            values.put("Latitud", cliente.getClienteLatitud());
            values.put("Longitud", cliente.getClienteLongitud());
            try {
                DataBaseHelper.myDataBase.insert("TB_Cliente", null, values);
            } catch (Exception ex){
            }
        }
    }
}
