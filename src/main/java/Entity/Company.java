package Entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Company {
    public Company() {
    }

    public Company(String name, String catchPhrase, String businessStrategy) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.businessStrategy = businessStrategy;
    }

    @Column(name = "company_name")
    String name;
    @Column(name = "company_catch_phrase")
    String catchPhrase;
    @Column(name = "company_business_strategy")
    String businessStrategy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    public String getBusinessStrategy() {
        return businessStrategy;
    }

    public void setBusinessStrategy(String businessStrategy) {
        this.businessStrategy = businessStrategy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;

        Company company = (Company) o;

        if (name != null ? !name.equals(company.name) : company.name != null) return false;
        if (catchPhrase != null ? !catchPhrase.equals(company.catchPhrase) : company.catchPhrase != null) return false;
        return businessStrategy != null ? businessStrategy.equals(company.businessStrategy) : company.businessStrategy == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (catchPhrase != null ? catchPhrase.hashCode() : 0);
        result = 31 * result + (businessStrategy != null ? businessStrategy.hashCode() : 0);
        return result;
    }
}
