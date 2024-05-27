package gm.inventarios.controller;

import gm.inventarios.exception.RecursoNoEncontradoException;
import gm.inventarios.model.Producto;
import gm.inventarios.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("inventario-app/v1/es/")
@CrossOrigin(value = "http://localhost:4200")
public class ProductoController {
    private static final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    // http://localhost:8080/inventario-app/v1/productos
    // Metodo de listar todos los productos
    @GetMapping("/productos")
    public List<Producto> obtenerProductos(){
        List<Producto> productos = this.productoService.listarProducto();

        logger.info("Productos obtenidos: ");
        productos.forEach((producto -> logger.info(producto.toString())));

        return productos;
    }

    // Metodo para obtener un producto pro id
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id){
        Producto producto = this.productoService.BuscarProductoPorId(id);
        if(producto != null)
            return ResponseEntity.ok(producto);
        else
            throw new RecursoNoEncontradoException("No se encontró el id: " + id);
    }

    // Metodo para crear un nuevo producto
    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto){
        // Impresion por consola
        logger.info("Producto a agregar: " + producto);

        return this.productoService.guardarProducto(producto);
    }

    // Metodo para editar producto
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable int id,
            @RequestBody Producto productoRecibido){
        // Buscar producto por id
        Producto producto = this.productoService.BuscarProductoPorId(id);
        if(producto == null)
            throw new RecursoNoEncontradoException("No se encontró el id: " + id);
        // Propiedades que se van actualizar
        producto.setDescripcion(productoRecibido.getDescripcion());
        producto.setPrecio(productoRecibido.getPrecio());
        producto.setExistencia(productoRecibido.getExistencia());

        // Metodo para guardar los datos
        this.productoService.guardarProducto(producto);

        // Retorno del dato
        return ResponseEntity.ok(producto);
    }

    // Metodo para eliminar producto
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable int id){
        // Buscar producto por id
        Producto producto = this.productoService.BuscarProductoPorId(id);
        if(producto == null)
            throw new RecursoNoEncontradoException("No se encontró el id: " + id);
        // Llamada al servicio para eliminar el registro
        this.productoService.eliminarProductoPorId(producto.getIdProducto());
        // Respuesta o validacion para confirmar que se elimino el producto
       Map<String, Boolean> response = new HashMap<>();
       response.put("eliminado", Boolean.TRUE);
        // Retorno o respueste al cliente sobre el metodo
       return  ResponseEntity.ok(response);
    }
}
