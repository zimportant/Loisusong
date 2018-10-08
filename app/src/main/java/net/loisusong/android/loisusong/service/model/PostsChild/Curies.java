package net.loisusong.android.loisusong.service.model.PostsChild;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import io.realm.RealmObject;

/**
 * Created by android on 1/29/18.
 */
public class Curies extends RealmObject {
    @SerializedName("name")
    public String name;

    @SerializedName("href")
    public String href;

    @SerializedName("templated")
    public boolean templated;
}
