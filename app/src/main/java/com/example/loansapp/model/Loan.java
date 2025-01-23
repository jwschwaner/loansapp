package com.example.loansapp.model;

/**
 * Modelklasse repræsenterer et udlån af en tablet.
 */
public class Loan {
    private int id;                  // Primærnøgle i databasen
    private String tabletBrand;      // Tablet-brand (påkrævet)
    private String cableType;        // Kabeltype (valgfri)
    private String borrowerName;     // Låners navn (påkrævet)
    private String borrowerContact;  // Låners kontaktinfo
    private String loanTime;         // Dato og tidspunkt (som String)
    private boolean isReturned;      // Om en tablet er afleveret (true/false)

    public Loan() {}

    public Loan(int id, String tabletBrand, String cableType, String borrowerName,
                String borrowerContact, String loanTime, boolean isReturned) {
        this.id = id;
        this.tabletBrand = tabletBrand;
        this.cableType = cableType;
        this.borrowerName = borrowerName;
        this.borrowerContact = borrowerContact;
        this.loanTime = loanTime;
        this.isReturned = isReturned;
    }

    // Getters og setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTabletBrand() {
        return tabletBrand;
    }

    public void setTabletBrand(String tabletBrand) {
        this.tabletBrand = tabletBrand;
    }

    public String getCableType() {
        return cableType;
    }

    public void setCableType(String cableType) {
        this.cableType = cableType;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerContact() {
        return borrowerContact;
    }

    public void setBorrowerContact(String borrowerContact) {
        this.borrowerContact = borrowerContact;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
