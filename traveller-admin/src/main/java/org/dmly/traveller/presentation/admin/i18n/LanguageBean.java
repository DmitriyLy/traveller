package org.dmly.traveller.presentation.admin.i18n;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named("language")
@SessionScoped
public class LanguageBean implements Serializable {
    private static final long serialVersionUID = -1622109063359055116L;

    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
    }

    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }
}
