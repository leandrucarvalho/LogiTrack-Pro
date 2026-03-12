const { createApp, ref, reactive, onMounted } = Vue;

createApp({
    setup() {
        const vehicles = ref([]);
        const selectedVehicleId = ref("");
        const maintenances = ref([]);
        const statuses = ["PENDENTE", "EM_REALIZACAO", "CONCLUIDA"];
        const error = ref("");
        const editingId = ref(null);
        const currentView = ref("dashboard");
        const showForm = ref(false);

        const dashboard = reactive({
            totalKmAll: 0,
            totalKmByVehicle: null,
            volumeByCategory: [],
            nextMaintenances: [],
            topUtilization: null,
            projectedMaintenanceCost: 0
        });

        const form = reactive({
            vehicleId: "",
            startDate: "",
            expectedEndDate: "",
            serviceType: "",
            estimatedCost: 0,
            status: "PENDENTE"
        });

        const fetchJson = async (url, options = {}) => {
            const response = await fetch(url, options);
            if (!response.ok) {
                const message = await response.text();
                throw new Error(message || "Erro ao processar requisicao");
            }
            if (response.status === 204) {
                return null;
            }
            return response.json();
        };

        const loadVehicles = async () => {
            vehicles.value = await fetchJson("/api/vehicles");
        };

        const loadDashboard = async () => {
            let url = "/api/dashboard";
            if (selectedVehicleId.value) {
                url += `?vehicleId=${selectedVehicleId.value}`;
            }
            const data = await fetchJson(url);
            dashboard.totalKmAll = data.totalKmAll ?? 0;
            dashboard.totalKmByVehicle = data.totalKmByVehicle ?? null;
            dashboard.volumeByCategory = data.volumeByCategory || [];
            dashboard.nextMaintenances = data.nextMaintenances || [];
            dashboard.topUtilization = data.topUtilization || null;
            dashboard.projectedMaintenanceCost = data.projectedMaintenanceCost ?? 0;
        };

        const loadMaintenances = async () => {
            maintenances.value = await fetchJson("/api/maintenances");
        };

        const resetForm = () => {
            editingId.value = null;
            form.vehicleId = "";
            form.startDate = "";
            form.expectedEndDate = "";
            form.serviceType = "";
            form.estimatedCost = 0;
            form.status = "PENDENTE";
            error.value = "";
        };

        const openForm = () => {
            resetForm();
            showForm.value = true;
        };

        const closeForm = () => {
            showForm.value = false;
            resetForm();
        };

        const editMaintenance = (maintenance) => {
            editingId.value = maintenance.id;
            form.vehicleId = maintenance.vehicle?.id || "";
            form.startDate = maintenance.startDate || "";
            form.expectedEndDate = maintenance.expectedEndDate || "";
            form.serviceType = maintenance.serviceType || "";
            form.estimatedCost = maintenance.estimatedCost ?? 0;
            form.status = maintenance.status || "PENDENTE";
            error.value = "";
            showForm.value = true;
        };

        const submitForm = async () => {
            try {
                error.value = "";
                const payload = {
                    vehicleId: form.vehicleId,
                    startDate: form.startDate,
                    expectedEndDate: form.expectedEndDate || null,
                    serviceType: form.serviceType,
                    estimatedCost: form.estimatedCost,
                    status: form.status
                };

                if (editingId.value) {
                    await fetchJson(`/api/maintenances/${editingId.value}`, {
                        method: "PUT",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(payload)
                    });
                } else {
                    await fetchJson("/api/maintenances", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(payload)
                    });
                }

                await loadMaintenances();
                await loadDashboard();
                closeForm();
            } catch (err) {
                error.value = err.message || "Erro ao salvar manutencao";
            }
        };

        const removeMaintenance = async (id) => {
            if (!window.confirm("Deseja excluir esta manutencao?")) {
                return;
            }
            try {
                await fetchJson(`/api/maintenances/${id}`, { method: "DELETE" });
                await loadMaintenances();
                await loadDashboard();
            } catch (err) {
                error.value = err.message || "Erro ao excluir manutencao";
            }
        };

        const logout = async () => {
            try {
                await fetch("/logout", { method: "POST" });
            } finally {
                window.location.href = "/login?logout=true";
            }
        };

        const syncViewFromHash = async () => {
            if (window.location.hash === "#maintenances") {
                currentView.value = "maintenances";
                await loadMaintenances();
            } else {
                currentView.value = "dashboard";
                await loadDashboard();
            }
            if (currentView.value === "dashboard") {
                showForm.value = false;
            }
        };

        onMounted(async () => {
            await loadVehicles();
            await loadDashboard();
            await loadMaintenances();
            await syncViewFromHash();
            window.addEventListener("hashchange", () => {
                syncViewFromHash();
            });
        });

        return {
            vehicles,
            selectedVehicleId,
            dashboard,
            maintenances,
            statuses,
            form,
            editingId,
            error,
            currentView,
            showForm,
            loadDashboard,
            resetForm,
            openForm,
            closeForm,
            editMaintenance,
            submitForm,
            removeMaintenance,
            logout
        };
    }
}).mount("#app");
