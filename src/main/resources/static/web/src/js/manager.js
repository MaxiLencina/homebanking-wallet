const { createApp } = Vue;

createApp({
  data() {
    return {
      data: null,
      clientes: [],
      url: "http://localhost:8080/api/cients",
      firstName: "",
      lastName: "",
      email: "",
      eliminarCliente: [],
    };
  },

  created() {
    this.loadData(this.url);
  },

  methods: {
    mostrarClientes() {
      console.log(this.clientes);
    },

    loadData(url) {
      axios
        .get(url)
        .then((resp) => {
          this.data = resp;
          this.clientes = this.data;
        })
        .catch((err) => console.log(err));
    },

    addClient() {
      let cliente = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
      };
      this.postClient(cliente);
    },
    postClient(cliente) {
      axios.post(this.url, cliente).then(() => {
        this.loadData(this.url);
      });
    },

    deleteClient(cliente) {
      axios.delete(cliente._links.clientes.href).then(() => {
        this.loadData(this.url);
      });
    },
  },
  computed: {
    /* botonEliminar() {
      this.deleteClient(clientes);
    }, */
  },
}).mount("#app");
