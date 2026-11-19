package com.gitb.juandaaguilera.Sistema.persistencia;

import com.gitb.juandaaguilera.Sistema.excepciones.BaseDatosException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// PATRON CREACIONAL - Singleton
// Solo existe UNA instancia de ConexionBD en todo el programa y todos los DAO comparten
// la misma conexion a MySQL. El constructor es privado: se obtiene con getInstancia().
public class ConexionBD {
    private static ConexionBD instancia;

    private final String url;
    private final String usuario;
    private final String clave;
    private Connection conexion;

    private ConexionBD() throws BaseDatosException {
        Properties datos = new Properties();
        try (InputStream entrada = ConexionBD.class.getResourceAsStream("/bd.properties")) {
            if (entrada != null) {
                datos.load(entrada);
            }
        } catch (IOException ex) {
            throw new BaseDatosException("No se pudo leer el archivo bd.properties", ex);
        }
        this.url = System.getProperty("bd.url",
                datos.getProperty("url", "jdbc:mysql://localhost:3306/cine_db?useSSL=false&allowPublicKeyRetrieval=true"));
        this.usuario = System.getProperty("bd.usuario", datos.getProperty("usuario", "root"));
        this.clave = System.getProperty("bd.clave", datos.getProperty("clave", ""));
    }

    public static synchronized ConexionBD getInstancia() throws BaseDatosException {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public synchronized Connection obtenerConexion() throws BaseDatosException {
        try {
            if (conexion == null || conexion.isClosed() || !conexion.isValid(2)) {
                conexion = DriverManager.getConnection(url, usuario, clave);
            }
            return conexion;
        } catch (SQLException ex) {
            throw new BaseDatosException("No se pudo conectar a la base de datos.\n"
                    + "Revise que MySQL esté iniciado en XAMPP y que haya importado database/cine_db.sql.\n"
                    + "Detalle: " + ex.getMessage(), ex);
        }
    }

    public synchronized void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ex) {
        }
    }
}
