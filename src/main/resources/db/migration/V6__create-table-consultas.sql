create table consultas(
id bigint not null auto_increment, 
medico bigint not null,
paciente bigint not null,
fecha datetime not null,

primary key(id),
constraint fk_consultas_medico_id foreign key(medico) references medicos(id),
constraint fk_consultas_paciente_id foreign key(paciente) references pacientes(id)

);