package gm.inventarios.service;

import gm.inventarios.model.Producto;

import java.util.List;

public interface IProductoService {
    public List<Producto> listarProducto();

    public Producto BuscarProductoPorId(Integer idProducto);

    public Producto guardarProducto(Producto producto);

    public void  eliminarProductoPorId(Integer idProducto);
}
