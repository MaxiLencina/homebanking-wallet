const { createApp } = Vue;

createApp({
  data() {
    return {
      cliente: [],
      cuentas: [],
      transacciones: [],
      account: [],
      prestamos: [],
      urlAPI: "/api/clients/current",
      active: false,
      sign: false,
      activeTab: "tab-1",
      eliminarBoton2: true,
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
          this.prestamos = this.cliente.loans;
        })
        .catch((err) => console.log(err));
    },

    crearCuenta() {
      axios
        .post("/api/clients/current/accounts")
        .then((response) => window.location.reload())
        .catch((err) => {
          this.eliminarBoton2 = false;
        });
    },

    /* eliminarBoton() {
      axios.get("/api/clients/current").then((res) => {
        console.log(res);
        if (res.data.accountsDTO.length == 3) {
          return false;
        } else return true;
      });
    }, 
    */

    /* eliminarBoton() {
      axios
        .get("/api/clients")
        .then((res) => {
          this.account = res.data.accountsDTO;
          if (this.account.length == 3) {
            return false;
          } else return true;
        })
        .catch((err) => console.log(err));
    },  */

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

    LogOut() {
      axios
        .post("/api/logout")
        .then((response) => (window.location.href = "/web/src/index.html"))
        .catch((err) => {
          console.log(err);
        });
    },
  },
}).mount("#app");
