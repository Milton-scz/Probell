package milo.probell.View.ProductoView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import milo.probell.Model.ProductoModel.Producto;
import milo.probell.R;

public class AlmacenProductoAdapter extends RecyclerView.Adapter<AlmacenProductoAdapter.ViewHolder> {
    private Context context;
    private List<Producto> productoList;
    private List<Producto> selectedProductos = new ArrayList<>();
    private OnSelectionChangeListener onSelectionChangeListener;

    public AlmacenProductoAdapter(Context context,List<Producto> productoList) {
        this.context = context;
        this.productoList = productoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_almacen_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto producto = productoList.get(position);
        holder.tvNombre.setText(producto.getNombre());
        holder.tvPrecio.setText(String.valueOf(producto.getPrecio()));
        holder.txtStockProducto.setText("Stock: " + producto.getStock());
        // Cargar imagen desde byte[]
        if (producto.getImagen() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(producto.getImagen(), 0, producto.getImagen().length);
            holder.imageViewProducto.setImageBitmap(bitmap);
        } else {
            holder.imageViewProducto.setImageResource(R.drawable.ic_launcher_background); // Imagen por defecto si no hay imagen
        }

        // Evento para hacer click en un producto
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditarProductoViewActivity.class);
            intent.putExtra("producto_id",  producto.getId());
            ((Activity) context).startActivityForResult(intent, 100);
        });


    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public void updateProductos(List<Producto> productos) {
        this.productoList = productos;
        notifyDataSetChanged();
    }

    // Agregar este método para actualizar la lista de productos
    public void setProductos(List<Producto> listaProductos) {
        this.productoList = listaProductos;
    }

    public void setOnSelectionChangeListener(OnSelectionChangeListener listener) {
        this.onSelectionChangeListener = listener;
    }

    public interface OnSelectionChangeListener {
        void onSelectionChanged(int selectedCount);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProducto;
        TextView tvNombre, tvPrecio, txtStockProducto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProducto = itemView.findViewById(R.id.imageViewProducto);
            tvNombre = itemView.findViewById(R.id.textViewNombre);
            txtStockProducto = itemView.findViewById(R.id.textViewStock);
            tvPrecio = itemView.findViewById(R.id.textViewPrecio);

        }
    }
}