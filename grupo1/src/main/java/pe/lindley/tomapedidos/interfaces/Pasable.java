package pe.lindley.tomapedidos.interfaces;

/**
 * Created by jlama on 21/09/2015.
 */
public interface Pasable {
    public String   ARG_CLIENTE         = "ARG_CLIENTE";
    public String   ARG_PRODUCTO        = "ARG_PRODUCTO";
    public String   ARG_PEDIDO          = "ARG_PEDIDO";

    public String   ARG_OPERACION       = "ARG_OPERACION";

    public int      ARG_CLIENTEPEDIDO           = 10;
    public int      ARG_PRODUCTOPEDIDO          = 20;
    public int      ARG_CLIENTEPRODUCTOPEDIDO   = 30;

    public int      OP_ADD                      = 1;
    public int      OP_MODIFY                   = 2;
    public int      OP_DELETE                   = 3;
}
