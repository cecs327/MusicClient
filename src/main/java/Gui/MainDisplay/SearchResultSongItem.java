package Gui.MainDisplay;

import app.Main;
import data.CollectionFormat;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import model.CollectionLightWeight;

public class SearchResultSongItem extends MainDisplayItem {

    public SearchResultSongItem(MainDisplayPresenter parent, CollectionLightWeight song) {
        super(parent, song);

        AnchorPane p = super.songPane;

        super.songPane.setOnDragDetected(e -> {
            Main.setCursorStyle(Cursor.CLOSED_HAND);

            Dragboard dragboard = p.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.put(CollectionFormat.FORMAT, this.song);
            dragboard.setContent(content);
            e.consume();

            // Create a "ghost" image of the pane on drag
            dragboard.setDragView(
                    view.snapshot(
                            new SnapshotParameters(),
                            new WritableImage(
                                    p.widthProperty().intValue(),
                                    p.heightProperty().intValue())));
        });
    }
}
