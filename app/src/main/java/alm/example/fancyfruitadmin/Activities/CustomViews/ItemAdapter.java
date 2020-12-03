package alm.example.fancyfruitadmin.Activities.CustomViews;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;

import alm.example.fancyfruitadmin.Pojos.Item;
import alm.example.fancyfruitadmin.Pojos.Product;
import alm.example.fancyfruitadmin.Pojos.Tag;
import alm.example.fancyfruitadmin.Providers.ProductProvider;
import alm.example.fancyfruitadmin.Providers.TagProvider;
import alm.example.fancyfruitadmin.R;
import alm.example.fancyfruitadmin.Utils.Helper;
import alm.example.fancyfruitadmin.databinding.ItemViewholderBinding;

public class ItemAdapter<T> extends RecyclerView.Adapter<ItemAdapter<T>.ItemViewHolder> {

    private ArrayList<T> itemList = new ArrayList<>();
    private ItemViewholderBinding binding;
    private ProductProvider productProvider;
    private TagProvider tagProvider;

    private static final String TAG = ItemAdapter.class.getSimpleName();

    public ItemAdapter(Context context) {
        productProvider = new ProductProvider(context);
        tagProvider = new TagProvider(context);
    }

    public void setItemList(ArrayList<T> itemList) {
        this.itemList = itemList;
    }

    public void setCollection(ArrayList<T> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        this.itemList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(T item, int position) {
        this.itemList.add(item);
        notifyItemInserted(position);
    }

    public void modifyItem(T item, int position) {
        this.itemList.set(position, item);
        notifyItemChanged(position, item);
    }

    public List<T> getItemList() {
        return itemList;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemViewholderBinding.inflate(
                LayoutInflater.from(parent.getContext())
        );
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewholder, parent, false);

        return new ItemViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        T item = itemList.get(position);

        if (item instanceof Product) {
            Product i = (Product) itemList.get(position);
            holder.itemClass = Product.class;
            holder.setUuid(i.getUuid());
            holder.setItemName(i.getName());
            holder.setItemRef(i.getRef());
            holder.setItemSlug(i.getSlug());
        } else {
            Tag i = (Tag) itemList.get(position);
            holder.itemClass = Tag.class;
            holder.setUuid(i.getUuid());
            holder.setItemName(i.getName());
        }

        holder.setItemPosition(position);
    }


    protected class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        protected Class<?> itemClass;
        private String uuid;
        private int position;
        private TextView itemName, itemRef, itemSlug;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemName = binding.itemName;
            this.itemRef = binding.itemReference;
            this.itemSlug = binding.itemSlug;

            itemView.setOnCreateContextMenuListener(this);

        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public TextView getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName.setText(itemName);
        }

        public TextView getItemRef() {
            return itemRef;
        }

        public void setItemRef(String itemRef) {
            this.itemRef.setText(itemRef);
        }

        public TextView getItemSlug() {
            return itemSlug;
        }

        public void setItemSlug(String itemSlug) {
            this.itemSlug.setText(itemSlug);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
            Edit.setOnMenuItemClickListener(onContextMenu);
            Delete.setOnMenuItemClickListener(onContextMenu);
        }

        private final MenuItem.OnMenuItemClickListener onContextMenu = item -> {

            switch (item.getItemId()) {
                case 1:

                    break;

                case 2:

                    // EJECUTAR EN EL HILO PRINCIPAL
                    itemView.post(() -> {
                        Helper.showMessageAlert(
                                "Aviso",
                                "¿Seguro que quieres borrar este item?",
                                itemView.getContext(),

                                // NUEVO THREAD PARA DELEGAR TRABAJO
                                () -> {
                                    Executors.newFixedThreadPool(2).execute(() -> {
                                        boolean ok = false;

                                        Log.e(TAG, item.getClass()+"" );

                                        if (itemClass == Tag.class) {
                                            ok = tagProvider.deleteTag(uuid); // LLAMADA A LA API
                                        } else if (itemClass == Product.class) {
                                            ok = productProvider.deleteProduct(uuid); // LLAMADA A LA API
                                        }

                                        boolean finalOk = ok;
                                        itemView.post(() -> {
                                            removeAndNotificate(finalOk);
                                        });

                                    });

                                    return null;
                                }
                        );
                    });
                    break;
            }
            return true;
        };

        public int getItemPosition() {
            return position;
        }

        public void setItemPosition(int position) {
            this.position = position;
        }

        private void removeAndNotificate(boolean ok) {
            if (ok) {
                removeItem(position);
                Snackbar.make(binding.getRoot(), "Item eliminado correctamente", BaseTransientBottomBar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(binding.getRoot(), "El item no se pudo eliminar", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }

    }
}
