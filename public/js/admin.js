// Base URL for the backend REST API
const API_BASE = 'http://localhost:8080/api';

document.addEventListener("DOMContentLoaded", () => {
    const path = window.location.pathname;
    
    if (path.includes("admin_categories.html")) loadCategories();
    else if (path.includes("admin_machines.html")) loadMachines();
    else if (path.includes("admin_manufacturers.html")) loadManufacturers();
    else if (path.includes("admin_pictures.html")) loadPictures();
    else if (path.includes("admin_specifications.html")) loadSpecifications();
});

// -------------------------------------------------------------------------------------------------- //
                                    //  LOAD FUNCTIONS   //
/** Load categories **/
async function loadCategories(){
    try{
        const res = await fetch(`${API_BASE}/categories`);
        const data = await res.json(); // Parse the JSON response
        
        document.getElementById('tableBody').innerHTML = data.map(cat => `
            <tr>
                <td>${cat.categoryId}</td>
                <td><strong>${cat.categoryName}</strong></td>
                <td><span class="badge ${cat.categoryType === 'Vehicle' ? 'bg-primary' : 'bg-success'}">${cat.categoryType}</span></td>
                <td>${cat.description}</td>
                <td><div class="path-cell">${cat.picture}</div></td>
                <td>
                    <button class="btn btn-sm btn-outline-warning" onclick="editCategory(${cat.categoryId})"><i class="bi bi-pencil"></i></button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteCategory(${cat.categoryId})"><i class="bi bi-trash"></i></button>
                </td>
            </tr>
        `).join('');
    }
    
    catch(err){console.error("Error fetching categories:", err);}
}

/** Load all machines **/
async function loadMachines(){
    try{
        const res = await fetch(`${API_BASE}/machines`);
        const data = await res.json();
        
        document.getElementById('machineTableBody').innerHTML = data.map(m => `
            <tr>
                <td>${m.machineId}</td>
                <td>${m.name}</td>
                <td>${Number(m.price).toLocaleString('de-DE')} EUR</td>
                <td>${m.releaseYear}</td>
                <td>${m.category ? m.category.categoryName : 'N/A'}</td>
                <td>${m.manufacturer ? m.manufacturer.name : 'N/A'}</td>
                <td>
                    <button class="btn btn-sm btn-outline-warning" onclick="editMachine(${m.machineId})"><i class="bi bi-pencil"></i></button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteMachine(${m.machineId})"><i class="bi bi-trash"></i></button>
                </td>
            </tr>
        `).join('');
    }
    
    catch(err){ 
        console.error("Error loading machines:", err); 
        document.getElementById('machineTableBody').innerHTML = '<tr><td colspan="7" class="text-center text-danger">Failed to load machines.</td></tr>';
    }
}

/** Load manufacturers **/
async function loadManufacturers(){
    try{
        const res = await fetch(`${API_BASE}/manufacturers`);
        const data = await res.json();
        
        document.getElementById('manufacturerTableBody').innerHTML = data.map(m => `
            <tr>
                <td>${m.manufacturerId}</td>
                <td><strong>${m.name}</strong></td>
                <td>${m.madeIn}</td>
                <td>
                    <div class="path-cell">
                        ${m.logo}
                    </div>
                </td>
                <td>
                    <button class="btn btn-sm btn-outline-warning" onclick="editManufacturer(${m.manufacturerId})"><i class="bi bi-pencil"></i></button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteManufacturer(${m.manufacturerId})"><i class="bi bi-trash"></i></button>
                </td>
            </tr>
        `).join('');
    }
    
    catch(err){console.error("Error:", err);}
}

/** Load pictures **/
async function loadPictures(){
    try{
        const res = await fetch(`${API_BASE}/machines`);
        const machines = await res.json();
        let allPictures = [];
        
        machines.forEach(m => {
            if (m.pictures && m.pictures.length > 0) {
                m.pictures.forEach(p => {
                    allPictures.push({ ...p, machineName: m.name, machineId: m.machineId });
                });
            }
        });
        
        allPictures.sort((a, b) => a.pictureId - b.pictureId);
        
        document.getElementById('pictureTableBody').innerHTML = allPictures.map(p => `
            <tr>
                <td>${p.pictureId}</td>
                <td><strong>${p.machineName}</strong></td>
                <td><div class="path-cell">${p.path}</div></td>
                <td>
                    <button class="btn btn-sm btn-outline-warning" onclick="editPicture(${p.pictureId}, ${p.machineId})"><i class="bi bi-pencil"></i></button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deletePicture(${p.pictureId})"><i class="bi bi-trash"></i></button>
                </td>
            </tr>
        `).join('');
    }
    
    catch(err){console.error("Error fetching pictures:", err);}
}

/** Load specifications **/
async function loadSpecifications(){
    try {
        const res = await fetch(`${API_BASE}/machines`);
        const machines = await res.json();
        let allSpecs = [];
        
        machines.forEach(m => {
            if (m.specifications && m.specifications.length > 0) {
                m.specifications.forEach(s => {
                    allSpecs.push({ ...s, machineName: m.name, machineId: m.machineId });
                });
            }
        });
        
        allSpecs.sort((a, b) => a.specificationId - b.specificationId);
        
        document.getElementById('specTableBody').innerHTML = allSpecs.map(s => `
            <tr>
                <td>${s.specificationId}</td>
                <td><strong>${s.machineName}</strong></td>
                <td>${s.name}</td>
                <td>${s.value}</td>
                <td>
                    <button class="btn btn-sm btn-outline-warning" onclick="editSpecification(${s.specificationId}, ${s.machineId})"><i class="bi bi-pencil"></i></button>
                    <button class="btn btn-sm btn-outline-danger" onclick="deleteSpecification(${s.specificationId})"><i class="bi bi-trash"></i></button>
                </td>
            </tr>
        `).join('');
    }
    
    catch(err){console.error("Error fetching specifications:", err);}
}

// ----------------------------------------------------------------------------------------------------------------------- //
                                            // ADD FUNCTIONS //

/** Modal to add a new Category. **/
async function openAddCategoryModal(){
    const { value: formValues } = await Swal.fire({
        title: 'Add New Category',
        html:
            '<input id="swal-name" class="swal2-input" placeholder="Category Name">' +
            '<select id="swal-type" class="swal2-select">' +
                '<option value="Vehicle">Vehicle</option>' + 
                '<option value="Machine">Machine</option>' +
            '</select>' +
            '<input id="swal-desc" class="swal2-input" placeholder="Description">' +
            '<input id="swal-pic" class="swal2-input" placeholder="Picture Path">',
        confirmButtonText: 'Add',
        preConfirm: () => ({
            categoryName: document.getElementById('swal-name').value,
            categoryType: document.getElementById('swal-type').value, 
            description: document.getElementById('swal-desc').value,
            picture: document.getElementById('swal-pic').value
        })
    });

    if(formValues){
        try {
            const res = await fetch(`${API_BASE}/categories`, { 
                method: 'POST', 
                headers: { 'Content-Type': 'application/json' }, 
                body: JSON.stringify(formValues) 
            });
            
            if(res.ok){ 
                Swal.fire('Success', 'Category added successfully!', 'success'); 
                loadCategories(); // Refresh the table
            }
            
            else{
                const err = await res.text();
                console.error("Backend Error:", err);
                Swal.fire('Error', 'Failed to add category.', 'error');
            }
        }
        catch (e){Swal.fire('Error', 'Unable to connect to the server', 'error');}
    }
}

/** Modal to add a new Machine. **/
async function openAddMachineModal(){

    const [categories, manufacturers] = await Promise.all([
        fetch(`${API_BASE}/categories`).then(r => r.json()),
        fetch(`${API_BASE}/manufacturers`).then(r => r.json())
    ]);

    
    const catOpts = categories.map(c => `<option value="${c.categoryId}">${c.categoryName}</option>`).join('');
    const manOpts = manufacturers.map(m => `<option value="${m.manufacturerId}">${m.name}</option>`).join('');

    const{ value: formValues } = await Swal.fire({
        title: 'Add New Machine',
        html:
            '<input id="swal-name" class="swal2-input" placeholder="Machine Name">' +
            '<input id="swal-price" class="swal2-input" type="number" placeholder="Price (€)">' +
            '<input id="swal-year" class="swal2-input" type="number" placeholder="Release Year">' +
            '<input id="swal-desc" class="swal2-input" placeholder="Description">' +
            '<input id="swal-icon" class="swal2-input" placeholder="Icon/Image Path">' +
            `<select id="swal-cat" class="swal2-select"><option value="" disabled selected>Select Category</option>${catOpts}</select>` +
            `<select id="swal-man" class="swal2-select"><option value="" disabled selected>Select Manufacturer</option>${manOpts}</select>`,
        confirmButtonText: 'Add',
        preConfirm: () => {
            const catId = document.getElementById('swal-cat').value;
            const manId = document.getElementById('swal-man').value;
            
            if(!catId || !manId){
                Swal.showValidationMessage('Category and Manufacturer are required!');
                return false;
            }

            return{
                name: document.getElementById('swal-name').value,
                price: parseFloat(document.getElementById('swal-price').value),
                releaseYear: parseInt(document.getElementById('swal-year').value),
                description: document.getElementById('swal-desc').value,
                iconPath: document.getElementById('swal-icon').value,
                category: { categoryId: parseInt(catId) },
                manufacturer: { manufacturerId: parseInt(manId) }
            };
        }
    });

    if(formValues){
        try{
            // Send POST request to create the new machine
            const res = await fetch(`${API_BASE}/machines`, { 
                method: 'POST', 
                headers: { 'Content-Type': 'application/json' }, 
                body: JSON.stringify(formValues) 
            });
            
            if(res.ok){ 
                Swal.fire('Success', 'Machine added successfully!', 'success'); 
                loadMachines(); // Refresh the machines table
            }
            
            else{
                const err = await res.text();
                console.error("Backend Error:", err);
                Swal.fire('Error', 'Failed to add machine.', 'error');
            }
        }
        
        catch(e){Swal.fire('Error', 'Unable to connect to the server', 'error');}
    }
}


/** Modal to add a new Manufacturer. **/
async function openAddManufacturerModal() {
    const{ value: formValues } = await Swal.fire({
        title: 'Add New Manufacturer',
        html:
            '<input id="swal-name" class="swal2-input" placeholder="Name">' +
            '<input id="swal-madein" class="swal2-input" placeholder="Made In (Country)">' +
            '<input id="swal-logo" class="swal2-input" placeholder="Logo Path/URL">',
        confirmButtonText: 'Add',
        preConfirm: () => ({
            name: document.getElementById('swal-name').value,
            madeIn: document.getElementById('swal-madein').value,
            logo: document.getElementById('swal-logo').value
        })
    });

    if(formValues){
        try{
            // Send POST request to create the new manufacturer
            const res = await fetch(`${API_BASE}/manufacturers`, { 
                method: 'POST', 
                headers: { 'Content-Type': 'application/json' }, 
                body: JSON.stringify(formValues) 
            });
            
            if(res.ok){ 
                Swal.fire('Success', 'Manufacturer added successfully!', 'success'); 
                loadManufacturers(); // Refresh the manufacturer table
            }
            
            else{
                const err = await res.text();
                console.error("Backend Error:", err);
                Swal.fire('Error', 'Failed to add manufacturer.', 'error');
            }
        }
        
        catch(e){Swal.fire('Error', 'Unable to connect to the server', 'error');}
    }

}

/** Modal to add a new Picture. **/
async function openAddPictureModal() {

    const machines = await fetch(`${API_BASE}/machines`).then(r => r.json());   
    const { value: formValues } = await Swal.fire({
        title: 'Add New Picture',
        html:
            '<input id="swal-path" class="swal2-input" placeholder="Picture Path/URL">' +
            `<select id="swal-mach" class="swal2-select"><option value="">Select Machine</option>${machines.map(m => `<option value="${m.machineId}">${m.name}</option>`).join('')}</select>`,
        confirmButtonText: 'Add',
        preConfirm: () => {
            const machId = document.getElementById('swal-mach').value;

            if(!machId){ 
                Swal.showValidationMessage('Select Machine'); 
                return false; 
            }

            return{
                path: document.getElementById('swal-path').value,
                machine: { machineId: parseInt(machId) }
            }
        }
    });

   if(formValues){
        try{
            // Send POST request to create the new picture
            const res = await fetch(`${API_BASE}/pictures`, { 
                method: 'POST', 
                headers: { 'Content-Type': 'application/json' }, 
                body: JSON.stringify(formValues) 
            });
            
            if(res.ok){ 
                Swal.fire('Success', 'Picture added successfully!', 'success'); 
                loadPictures(); // Refresh the picture table
            }
            
            else{
                const err = await res.text();
                console.error("Backend Error:", err);
                Swal.fire('Error', 'Failed to add picture.', 'error');
            }
        }
        
        catch(e){Swal.fire('Error', 'Unable to connect to the server', 'error');}
    }

}

/** Modal to add a new Specification. **/
async function openAddSpecModal(){
    const machines = await fetch(`${API_BASE}/machines`).then(r => r.json());
    const { value: formValues } = await Swal.fire({
        title: 'Add New Specification',
        html:
            '<input id="swal-name" class="swal2-input" placeholder="Key (e.g. Engine)">' +
            '<input id="swal-value" class="swal2-input" placeholder="Value (e.g. 500HP)">' +
            `<select id="swal-mach" class="swal2-select"><option value="">Select Machine</option>${machines.map(m => `<option value="${m.machineId}">${m.name}</option>`).join('')}</select>`,
        confirmButtonText: 'Add',
        preConfirm: () => {
            const machId = document.getElementById('swal-mach').value;
            if(!machId) { Swal.showValidationMessage('Select Machine'); return false; }
            return {
                name: document.getElementById('swal-name').value,
                value: document.getElementById('swal-value').value,
                machine: { machineId: parseInt(machId) }
            }
        }
    });

       if(formValues){
        try{
            // Send POST request to create the new specification
            const res = await fetch(`${API_BASE}/specifications`, { 
                method: 'POST', 
                headers: { 'Content-Type': 'application/json' }, 
                body: JSON.stringify(formValues) 
            });
            
            if(res.ok){ 
                Swal.fire('Success', 'Manufacturer added successfully!', 'success'); 
                loadSpecifications(); // Refresh the specification table
            }
            
            else{
                const err = await res.text();
                console.error("Backend Error:", err);
                Swal.fire('Error', 'Failed to add specification.', 'error');
            }

        }
        catch(e){Swal.fire('Error', 'Unable to connect to the server', 'error');}
    }

}

// ------------------------------------------------------------------------------------------------------------------------ //
                                            // EDIT FUNCTIONS //

/** Loads category details into a modal for editing, then sends a PUT request. **/
async function editCategory(id) {

    const res = await fetch(`${API_BASE}/categories/${id}`);
    const cat = await res.json();
    const { value: formValues } = await Swal.fire({
        title: 'Edit Category',
        html:
            `<input id="swal-name" class="swal2-input" value="${cat.categoryName}">` +
            `<input id="swal-type" class="swal2-input" value="${cat.categoryType}">` +
            `<input id="swal-desc" class="swal2-input" value="${cat.description}">` +
            `<input id="swal-pic" class="swal2-input" value="${cat.picture}">`,
        confirmButtonText: 'Save Changes',
        preConfirm: () => ({
            categoryId: id,
            categoryName: document.getElementById('swal-name').value,
            categoryType: document.getElementById('swal-type').value,
            description: document.getElementById('swal-desc').value,
            picture: document.getElementById('swal-pic').value
        })
    });

    if(formValues){
        await fetch(`${API_BASE}/categories/${id}`, { 
            method: 'PUT', 
            headers: { 'Content-Type': 'application/json' }, 
            body: JSON.stringify(formValues) 
        });

        Swal.fire('Updated!', 'Category updated successfully.', 'success');
        loadCategories();
    }
}

/** Loads machine details and required relationships (categories, manufacturers) for editing. **/
async function editMachine(id) {

    const [categories, manufacturers, machineRes] = await Promise.all([
        fetch(`${API_BASE}/categories`).then(r => r.json()),
        fetch(`${API_BASE}/manufacturers`).then(r => r.json()),
        fetch(`${API_BASE}/machines/${id}`).then(r => r.json())
    ]);
    const m = machineRes;

    // Generate option tags and pre-select the currently associated category/manufacturer
    const catOpts = categories.map(c => `<option value="${c.categoryId}" ${m.category && m.category.categoryId === c.categoryId ? 'selected' : ''}>${c.categoryName}</option>`).join('');
    const manOpts = manufacturers.map(man => `<option value="${man.manufacturerId}" ${m.manufacturer && m.manufacturer.manufacturerId === man.manufacturerId ? 'selected' : ''}>${man.name}</option>`).join('');

    const { value: formValues } = await Swal.fire({
        title: 'Edit Machine',
        html:
            `<input id="swal-name" class="swal2-input" value="${m.name}">` +
            `<input id="swal-price" class="swal2-input" type="number" value="${m.price}">` +
            `<input id="swal-year" class="swal2-input" type="number" value="${m.releaseYear}">` +
            `<input id="swal-desc" class="swal2-input" value="${m.description}">` +
            `<input id="swal-icon" class="swal2-input" value="${m.iconPath}">` +
            `<select id="swal-cat" class="swal2-select">${catOpts}</select>` +
            `<select id="swal-man" class="swal2-select">${manOpts}</select>`,
        confirmButtonText: 'Save Changes',
        preConfirm: () => ({
            machineId: id,
            name: document.getElementById('swal-name').value,
            price: document.getElementById('swal-price').value,
            releaseYear: parseInt(document.getElementById('swal-year').value),
            description: document.getElementById('swal-desc').value,
            iconPath: document.getElementById('swal-icon').value,
            category: { categoryId: parseInt(document.getElementById('swal-cat').value) },
            manufacturer: { manufacturerId: parseInt(document.getElementById('swal-man').value) }
        })
    });

    if(formValues){
        await fetch(`${API_BASE}/machines/${id}`, { 
            method: 'PUT', 
            headers: { 'Content-Type': 'application/json' }, 
            body: JSON.stringify(formValues) 
        });

        Swal.fire('Updated!', 'Machine updated successfully.', 'success');
        loadMachines();
    }
}

/** Loads manufacturer details for editing. **/
async function editManufacturer(id){
    const res = await fetch(`${API_BASE}/manufacturers/${id}`);
    const man = await res.json();

    const { value: formValues } = await Swal.fire({
        title: 'Edit Manufacturer',
        html:
            `<input id="swal-name" class="swal2-input" value="${man.name}">` +
            `<input id="swal-madein" class="swal2-input" value="${man.madeIn}">` +
            `<input id="swal-logo" class="swal2-input" value="${man.logo}">`,
        confirmButtonText: 'Save Changes',
        preConfirm: () => ({
            manufacturerId: id,
            name: document.getElementById('swal-name').value,
            madeIn: document.getElementById('swal-madein').value,
            logo: document.getElementById('swal-logo').value
        })
    });

    if(formValues){
        await fetch(`${API_BASE}/manufacturers/${id}`, { 
            method: 'PUT', 
            headers: { 'Content-Type': 'application/json' }, 
            body: JSON.stringify(formValues) 
        });

        Swal.fire('Updated!', 'Manufacturer updated successfully.', 'success');
        loadManufacturers();
    }
}

/** Loads picture details for editing. **/

async function editPicture(id, currentMachineId){
    const [machines, picRes] = await Promise.all([
        fetch(`${API_BASE}/machines`).then(r => r.json()),
        fetch(`${API_BASE}/pictures/${id}`).then(r => r.json())
    ]);
    const p = picRes;

    const machOpts = machines.map(m => `<option value="${m.machineId}" ${m.machineId === currentMachineId ? 'selected' : ''}>${m.name}</option>`).join('');
    const { value: formValues } = await Swal.fire({
        title: 'Edit Picture',
        html:
            `<input id="swal-path" class="swal2-input" value="${p.path}">` +
            `<select id="swal-mach" class="swal2-select">${machOpts}</select>`,
        confirmButtonText: 'Save Changes',
        preConfirm: () => ({
            pictureId: id,
            path: document.getElementById('swal-path').value,
            machine: { machineId: parseInt(document.getElementById('swal-mach').value) }
        })
    });

    if(formValues){
        await fetch(`${API_BASE}/pictures/${id}`, { 
            method: 'PUT', 
            headers: { 'Content-Type': 'application/json' }, 
            body: JSON.stringify(formValues) 
        });

        Swal.fire('Updated!', 'Picture updated successfully.', 'success');
        loadPictures();
    }
}

/** Loads specification details for editing. **/

async function editSpecification(id, currentMachineId) {
    const [machines, specRes] = await Promise.all([
        fetch(`${API_BASE}/machines`).then(r => r.json()),
        fetch(`${API_BASE}/specifications/${id}`).then(r => r.json())
    ]);
    const s = specRes;
    const machOpts = machines.map(m => `<option value="${m.machineId}" ${m.machineId === currentMachineId ? 'selected' : ''}>${m.name}</option>`).join('');

    const{value: formValues} = await Swal.fire({
        title: 'Edit Specification',
        html:
            `<input id="swal-name" class="swal2-input" value="${s.name}">` +
            `<input id="swal-value" class="swal2-input" value="${s.value}">` +
            `<select id="swal-mach" class="swal2-select">${machOpts}</select>`,
        confirmButtonText: 'Save Changes',
        preConfirm: () => ({
            specificationId: id,
            name: document.getElementById('swal-name').value,
            value: document.getElementById('swal-value').value,
            machine: { machineId: parseInt(document.getElementById('swal-mach').value) }
        })
    });

    if(formValues){
        await fetch(`${API_BASE}/specifications/${id}`, { 
            method: 'PUT', 
            headers: { 'Content-Type': 'application/json' }, 
            body: JSON.stringify(formValues) 
        });

        Swal.fire('Updated!', 'Specification updated successfully.', 'success');
        loadSpecifications();
    }
}

// ------------------------------------------------------------------------------------------------------ //
                                         // DELETE FUNCTIONS //

async function confirmDelete(url, loadCallback) {
    // Show a warning modal asking for confirmation
    const result = await Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#dc3545',
        confirmButtonText: 'Yes, delete it!'
    });

    // Proceed only if the user clicked the confirm button
    if (result.isConfirmed){
        try {
            await fetch(url, { method: 'DELETE' });                      
            Swal.fire('Deleted!', 'Item has been deleted.', 'success');
            loadCallback();                                               
        } 
        catch (err){Swal.fire('Error!', 'Something went wrong while deleting.', 'error');}
    }
}

// Wrapper functions to handle deletion for specific entities
function deleteCategory(id){confirmDelete(`${API_BASE}/categories/${id}`, loadCategories);}
function deleteMachine(id){confirmDelete(`${API_BASE}/machines/${id}`, loadMachines);}
function deleteManufacturer(id){confirmDelete(`${API_BASE}/manufacturers/${id}`, loadManufacturers);}
function deletePicture(id){confirmDelete(`${API_BASE}/pictures/${id}`, loadPictures);}
function deleteSpecification(id){confirmDelete(`${API_BASE}/specifications/${id}`, loadSpecifications);}
// ---------------------------------------------------------------------------------------------------------- //
