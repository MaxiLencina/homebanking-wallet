const { createApp } = Vue;

createApp({
  data() {
    return {
      cliente: [],
      cuentas: [],
      transacciones: [],
      cards: [],
      urlAPI: "/api/clients/current",
      active: false,
      sign: false,
      activeTab: "tab-1",
      alertError: null,
      cardType: "none",
      active: false,
      cardColor: "none",
    };
  },

  created() {
    this.loadData(this.urlAPI);
  },

  methods: {
    loadData(url) {
      axios
        .get(url)
        .then((resp) => {
          this.cliente = resp.data;
          this.cuentas = this.cliente.accountsDTO;
          this.cards = this.cliente.cards;
        })
        .catch((err) => console.log(err));
    },

    clickMenuDos() {
      this.active = !this.active;
    },

    LogOut() {
      axios
        .post("/api/logout")
        .then((response) => (window.location.href = "/web/src/index.html"))
        .catch((err) => {
          console.log(err);
        });
    },

    createCard(event) {
      event.preventDefault();
      if (this.cardType == "none" || this.cardColor == "none") {
        console.log(this.cardType, this.cardColor);
      } else {
        axios
          .post(
            `/api/clients/current/cards?cardType=${this.cardType}&cardColor=${this.cardColor}`
          )
          .then((res) => (window.location.href = "/web/src/pages/cards.html"))
          .catch((err) => {
            console.log(err);
          });
      }
    },

    mostrarAlert(color, type) {
      if (color == "none" || type == "none") {
        return true;
      } else return false;
    },
  },
}).mount("#app");
