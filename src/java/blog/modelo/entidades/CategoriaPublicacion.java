package blog.modelo.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "categoria_publicacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaPublicacion.findAll", query = "SELECT c FROM CategoriaPublicacion c")
    , @NamedQuery(name = "CategoriaPublicacion.findById", query = "SELECT c FROM CategoriaPublicacion c WHERE c.id = :id")
    , @NamedQuery(name = "CategoriaPublicacion.findByNombre", query = "SELECT c FROM CategoriaPublicacion c WHERE c.nombre = :nombre")})
public class CategoriaPublicacion implements Serializable {

    @Basic(optional = false)
    @Column(name = "nombre_plural")
    private String nombrePlural;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaPublicacionId", fetch = FetchType.EAGER)
    private List<Publicacion> publicacionList;

    public CategoriaPublicacion() {
    }

    public CategoriaPublicacion(Integer id) {
        this.id = id;
    }

    public CategoriaPublicacion(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Publicacion> getPublicacionList() {
        return publicacionList;
    }

    public void setPublicacionList(List<Publicacion> publicacionList) {
        this.publicacionList = publicacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaPublicacion)) {
            return false;
        }
        CategoriaPublicacion other = (CategoriaPublicacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "blog.modelo.entidades.CategoriaPublicacion[ id=" + id + " ]";
    }

    public String getNombrePlural() {
        return nombrePlural;
    }

    public void setNombrePlural(String nombrePlural) {
        this.nombrePlural = nombrePlural;
    }

}
