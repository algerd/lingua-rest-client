
package ru.javafx.lingua.rest.client.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.javafx.lingua.rest.client.core.Entity;
import ru.javafx.lingua.rest.client.core.datacore.RelPath;
import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDateTime;

@RelPath("words")
public class Word implements Entity {

    private final StringProperty word = new SimpleStringProperty("");
    private final StringProperty transcription = new SimpleStringProperty("");
    private final StringProperty translation = new SimpleStringProperty("");
    private LocalDateTime created = LocalDateTime.now();
    
    public Word() {}
       
    public String getWord() {
        return word.get();
    }
    public void setWord(String value) {
        word.set(value);
    }
    public StringProperty wordProperty() {
        return word;
    }
    
    public String getTranscription() {
        return transcription.get();
    }
    public void setTranscription(String value) {
        transcription.set(value);
    }
    public StringProperty transcriptionProperty() {
        return transcription;
    }
    
    public String getTranslation() {
        return translation.get();
    }
    public void setTranslation(String value) {
        translation.set(value);
    }
    public StringProperty translationProperty() {
        return translation;
    }

    public LocalDateTime getCreated() {
        return created;
    }
    
    @JsonDeserialize(using = JsonDateDeserializer.class)
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
    
    @Override
    public Word clone() {
        Word w = new Word();
        w.setWord(getWord());
        w.setTranscription(getTranscription());
        w.setTranslation(getTranslation());      
        w.setCreated(created);
        return w;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.word);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Word other = (Word) obj;
        return Objects.equals(this.word, other.word);
    }

    @Override
    public String toString() {
        return "Word{" + "word=" + word + ", transcription=" + transcription + ", translation=" + translation + ", created=" + created + '}';
    }  
   
}
