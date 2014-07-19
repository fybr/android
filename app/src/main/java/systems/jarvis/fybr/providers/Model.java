package systems.jarvis.fybr.providers;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public abstract class Model {

    public Model() {
        type = this.getClass().getSimpleName().toLowerCase();
        id = UUID.randomUUID().toString();
        created = new Date();
    }

    public String type;

    public String id;

    public Date created;

}
