const { createApp } = Vue;

createApp({
  data() {
    return {
      active: false,
      accountData: [],
      transacciones: [],
    };
  },

  created() {
    this.buscarDatos();
  },

  methods: {
    clickMenuDos() {
      this.active = !this.active;
    },

    buscarDatos() {
      const urlParams = new URLSearchParams(window.location.search);
      const id = urlParams.get("id");
      axios
        .get(`/api/accounts/current/${id}`)
        .then((resp) => {
          this.accountData = resp.data;
          this.transacciones = this.accountData.transactions;
        })
        .catch((err) => console.log(err));
    },

    formatearFecha(fecha) {
      return new Date(fecha).toLocaleDateString("en-GB");
    },

    pintarCeldas(monto) {
      if (monto > 0) {
        return true;
      } else return false;
    },
  },
}).mount("#app");
