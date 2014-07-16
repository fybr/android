package systems.jarvis.fybr.providers;

import java.io.Serializable;

public abstract class Model {

    public Model() {
        type = this.getClass().getSimpleName().toLowerCase();
    }

    public String type;

}
