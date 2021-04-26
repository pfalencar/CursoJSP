package beans;

public class BeanCursoJsp {
	
	private Long id;
	private String login;
	private String senha;
	private String nome;
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private String ibge;
	private String foto;	
	private String contentType;	
	private String tempFotoUser;	
	private String miniaturaFoto;
	private String curriculoBase64;
	private String contentTypeCurriculo;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getIbge() {
		return ibge;
	}
	public void setIbge(String ibge) {
		this.ibge = ibge;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String fotoBase64) {
		this.foto = fotoBase64;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}	
	
	public String getTempFotoUser() {
		tempFotoUser = "data:" + this.contentType + ";base64," + this.foto;
		return tempFotoUser;
	}		
	
	public void setMiniaturaFoto(String miniaturaFoto) {
		this.miniaturaFoto = miniaturaFoto;
	}	
	public String getMiniaturaFoto() {
		return miniaturaFoto;
	}
	
	
	public String getCurriculoBase64() {
		return curriculoBase64;
	}
	public void setCurriculoBase64(String curriculoBase64) {
		this.curriculoBase64 = curriculoBase64;
	}
	public String getContentTypeCurriculo() {
		return contentTypeCurriculo;
	}
	public void setContentTypeCurriculo(String contentTypeCurriculo) {
		this.contentTypeCurriculo = contentTypeCurriculo;
	}
	
}

