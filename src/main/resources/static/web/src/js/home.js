const { createApp } = Vue;

createApp({
  data() {
    return {
      cliente: [],
      cuentas: [],
      transacciones: [],
      prestamos: [],
      urlAPI: "http://localhost:8080/api/clients/1",
      urlAccount: "http://localhost:8080/api/accounts",
      active: false,
      sign: false,
    };
  },

  created() {
    this.loadData(this.urlAPI);
    this.loadAccount(this.urlAccount);
  },

  methods: {
    loadData(url) {
      axios
        .get(url)
        .then((resp) => {
          this.cliente = resp.data;

          this.transacciones = this.cuentas.transactions;
          this.prestamos = this.cliente.loans;
        })
        .catch((err) => console.log(err));
    },

    loadAccount(url) {
      axios
        .get(url)
        .then((resp) => {
          this.cuentas = resp.data;
        })
        .catch((err) => console.log(err));
    },

    clickMenuDos() {
      this.active = !this.active;
    },

    clickSign() {
      this.sign = !this.sign;
    },

    pruebaDelPost() {
      axios
        .post(
          "/api/clients",
          "firstName=pedro2&lastName=rodriguez&email=asdasd@asd.com&password=123456"
        )
        .then((response) => console.log("registrado!"));
    },
  },
  mounted() {
    this.pruebaDelPost();
  },
}).mount("#app");
