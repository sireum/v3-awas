package org.sireum.awas.awasfacade;

import org.sireum.awas.ast.AwasSerializer$;
import org.sireum.awas.ast.Model;

import java.util.Optional;

import static org.sireum.awas.util.JavaConverters.toJavaOptional;

public class AwasSerializer {
    public static String serialize(Model model) {
        return AwasSerializer$.MODULE$.apply(model);
    }

    public static Optional<Model> deserialize(String json) {
        return toJavaOptional(AwasSerializer$.MODULE$.unapply(json));
    }
}
