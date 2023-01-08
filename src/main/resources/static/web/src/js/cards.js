const { createApp } = Vue;

createApp({
  data() {
    return {
      clientes: [],
      active: false,
      sign: false,
      activeTab: "tab-1",
      tarjetasDeCredito: null,
      tarjetasDeDebito: null,
    };
  },

  created() {
    this.loadData();
  },

  methods: {
    loadData() {
      axios
        .get("/api/clients/current")
        .then((response) => {
          this.clientes = response.data;
          this.tarjetasDeCredito = this.clientes.cards.filter(
            (card) => card.type == "CREDIT"
          );
          this.tarjetasDeDebito = this.clientes.cards.filter(
            (card) => card.type == "DEBIT"
          );
        })
        .catch((err) => {
          console.log(err);
        });
    },
    LogOut() {
      axios
        .post("/api/logout")
        .then((response) => (window.location.href = "/web/src/index.html"))
        .catch((err) => {
          console.log(err);
        });
    },

    clickMenuDos() {
      this.active = !this.active;
    },

    clickSign() {
      this.sign = !this.sign;
    },

    setActive(tab) {
      this.activeTab = tab;
    },
    isActive(tab) {
      return this.activeTab === tab;
    },
    prueba() {
      return false;
    },
  },
}).mount("#app");
