package alm.example.fancyfruitadmin.Pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;

public class Product implements Serializable {

    @SerializedName(value = "uuid")
    private String uuid;
    @SerializedName(value = "ref")
    private String ref;
    @SerializedName(value = "name")
    private String name;
    @SerializedName(value = "slug")
    private String slug;
    @SerializedName(value = "tags")
    private Tag[] tags;

    public Product(String uuid, String ref, String name, String slug, Tag[] tags) {
        this.uuid = uuid;
        this.ref = ref;
        this.name = name;
        this.slug = slug;
        this.tags = tags;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Product{" +
                "uuid='" + uuid + '\'' +
                ", ref='" + ref + '\'' +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
