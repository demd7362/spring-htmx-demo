package com.api.sample.common.tool;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public final class Html {
    private final Deque<Html> childrens = new ArrayDeque<>();
    private final List<Html> brothers = new ArrayList<>();
    private final String tag;
    private final Map<String, String> attributes;
    private final Set<String> classes = new HashSet<>();
    private static final String CONTENT_KEY = "text";
    private static final String TEMPLATE = "<%s%s>%s%s</%s>";

    private String getAttributesAsString() {
        String attrs = this.attributes.entrySet().stream()
                .filter(e -> !e.getKey().equals(CONTENT_KEY))
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(" "));
        if (StringUtils.hasText(attrs)) {
            return " " + attrs;
        }
        return "";
    }

    public static Html javaScript(String javaScript) {
        return new Html("script", CONTENT_KEY, javaScript);
    }

    public static Html openDialog(String title, String content, String callback) {
        if (!callback.startsWith("()") || !callback.startsWith("function")) {
            callback = "function(){ " + callback + " }";
        }
        return javaScript("openDialog('%s','%s',%s)".formatted(title, content, callback));
    }

    public static Html openDialog(String title, String content) {
        return javaScript("openDialog('%s','%s')".formatted(title, content));
    }

    public boolean hasClass(String className) {
        return this.classes.contains(className);
    }

    public void removeClass(String... classNames) {
        String classes = this.attributes.get("class");
        if (classes == null) {
            return;
        }
        for (String className : classNames) {
            classes = classes.replaceAll(className, "");
            this.classes.remove(className);
        }
        this.attributes.put("class", classes);
    }

    public void addClass(String... classNames) {
        String classes = this.attributes.get("class");
        if (classes == null) {
            this.attributes.put("class", String.join(" ", classNames));
            return;
        }
        StringBuilder classBuilder = new StringBuilder(classes);
        for (String className : classNames) {
            classBuilder.append(" ").append(className);
            this.classes.add(className);
        }
        this.attributes.put("class", classBuilder.toString());
    }

    public static Html createRoot(@NonNull String tag, @Nullable Map<String, String> attributes) {
        return new Html(tag, attributes);
    }

    public static Html createRoot(@NonNull String tag, String... attributes) {
        return new Html(tag, attributes);
    }

    private Html(@NonNull String tag, @Nullable Map<String, String> attributes) {
        this.attributes = new HashMap<>();
        addAttributes(attributes);
        this.tag = tag;
    }

    private Html(@NonNull String tag, String... attributes) {
        this.attributes = new HashMap<>();
        addAttributes(attributes);
        this.tag = tag;
    }

    public void addAttributes(String... attributes) {
        if (attributes.length % 2 != 0) {
            throw new IllegalArgumentException("Attributes must be even. " + this);
        }
        for (int i = 0; i < attributes.length; i += 2) {
            if (StringUtils.hasText(attributes[i])) {
                if (attributes[i].equals("class")) {
                    this.classes.add(attributes[i + 1]);
                }
                if (attributes[i].equals("style")) {
                    this.attributes.put(attributes[i], attributes[i + 1].replaceAll(" ", ""));
                } else {
                    this.attributes.put(attributes[i], attributes[i + 1]);
                }
            }
        }
    }

    public void addAttributes(Map<String, String> attributes) {
        if (attributes == null) {
            return;
        }
        for (var attribute : attributes.entrySet()) {
            if (StringUtils.hasText(attribute.getKey())) {
                if (attribute.getKey().equals("class")) {
                    this.classes.add(attribute.getKey());
                }
                if (attribute.getKey().equals("style")) {
                    this.attributes.put(attribute.getKey(), attribute.getValue().replaceAll(" ", ""));
                } else {
                    this.attributes.put(attribute.getKey(), attribute.getValue());
                }
            }
        }
    }


    public Html findById(String id) {
        return find(this, id);
    }

    private Html find(Html html, String id) {
        if (id.equals(html.attributes.get("id"))) {
            return html;
        }
        for (Html children : html.childrens) {
            Html result = find(children, id);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public Html putFirst(String tag, String... attributes) {
        return this.putFirst(new Html(tag, attributes));
    }

    public Html putFirst(String tag, Map<String, String> attributes) {
        return this.putFirst(new Html(tag, attributes));
    }

    public Html putFirst(Html html) {
        this.childrens.addFirst(html);
        return html;
    }

    public Html putLast(String tag, String... attributes) {
        return this.putLast(new Html(tag, attributes));
    }

    public Html putLast(String tag, Map<String, String> attributes) {
        return this.putLast(new Html(tag, attributes));
    }

    public Html putLast(Html html) {
        this.childrens.addLast(html);
        return html;
    }

    public Html append(String tag, Map<String, String> attributes) {
        return append(new Html(tag, attributes));
    }

    public Html append(String tag, String... attributes) {
        return append(new Html(tag, attributes));
    }

    public Html append(Html html) {
        this.brothers.add(html);
        return html;
    }


    @Override
    public String toString() {
        return toString(this);
    }

    private String toString(Html html) {
        StringBuilder builder = new StringBuilder();
        for (Html children : html.childrens) {
            builder.append(children.toString());
        }
        String text = html.attributes.getOrDefault(CONTENT_KEY, "");
        String formattedElement = TEMPLATE.formatted(html.tag, html.getAttributesAsString(), text, builder.toString(), html.tag);
        StringBuilder element = new StringBuilder(formattedElement);
        for (Html brother : html.brothers) {
            element.append(brother.toString());
        }
        return element.toString();
    }
}
